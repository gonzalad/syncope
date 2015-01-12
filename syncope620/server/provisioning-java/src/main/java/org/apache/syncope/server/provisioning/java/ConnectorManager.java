/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.server.provisioning.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.syncope.common.lib.types.ConnConfProperty;
import org.apache.syncope.server.persistence.api.dao.ExternalResourceDAO;
import org.apache.syncope.server.persistence.api.entity.ConnInstance;
import org.apache.syncope.server.persistence.api.entity.ExternalResource;
import org.apache.syncope.server.provisioning.api.ConnIdBundleManager;
import org.apache.syncope.server.provisioning.api.Connector;
import org.apache.syncope.server.provisioning.api.ConnectorFactory;
import org.apache.syncope.server.provisioning.api.ConnectorRegistry;
import org.apache.syncope.server.misc.spring.ApplicationContextProvider;
import org.identityconnectors.common.l10n.CurrentLocale;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Load ConnId connector instances.
 */
@Component
public class ConnectorManager implements ConnectorRegistry, ConnectorFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorManager.class);

    @Autowired
    private ConnIdBundleManager connIdBundleManager;

    @Autowired
    private ExternalResourceDAO resourceDAO;

    private String getBeanName(final ExternalResource resource) {
        return String.format("connInstance-%d-%s", resource.getConnector().getKey(), resource.getKey());
    }

    @Override
    public Connector getConnector(final ExternalResource resource) {
        // Try to re-create connector bean from underlying resource (useful for managing failover scenarios)
        if (!ApplicationContextProvider.getBeanFactory().containsBean(getBeanName(resource))) {
            registerConnector(resource);
        }

        return (Connector) ApplicationContextProvider.getBeanFactory().getBean(getBeanName(resource));
    }

    @Override
    public Connector createConnector(final ConnInstance connInstance, final Set<ConnConfProperty> configuration) {
        final ConnInstance connInstanceClone = SerializationUtils.clone(connInstance);

        connInstanceClone.setConfiguration(configuration);

        Connector connector = new ConnectorFacadeProxy(connInstanceClone);
        ApplicationContextProvider.getBeanFactory().autowireBean(connector);

        return connector;
    }

    @Override
    public ConnInstance getOverriddenConnInstance(final ConnInstance connInstance,
            final Set<ConnConfProperty> overridden) {
        final Set<ConnConfProperty> configuration = new HashSet<>();
        final Map<String, ConnConfProperty> overridable = new HashMap<>();

        // add not overridable properties
        for (ConnConfProperty prop : connInstance.getConfiguration()) {
            if (prop.isOverridable()) {
                overridable.put(prop.getSchema().getName(), prop);
            } else {
                configuration.add(prop);
            }
        }

        // add overridden properties
        for (ConnConfProperty prop : overridden) {
            if (overridable.containsKey(prop.getSchema().getName()) && !prop.getValues().isEmpty()) {
                configuration.add(prop);
                overridable.remove(prop.getSchema().getName());
            }
        }

        // add overridable properties not overridden
        configuration.addAll(overridable.values());

        connInstance.setConfiguration(configuration);

        return connInstance;
    }

    @Override
    public void registerConnector(final ExternalResource resource) {
        final ConnInstance connInstance = getOverriddenConnInstance(
                SerializationUtils.clone(resource.getConnector()), resource.getConnInstanceConfiguration());
        final Connector connector = createConnector(resource.getConnector(), connInstance.getConfiguration());
        LOG.debug("Connector to be registered: {}", connector);

        final String beanName = getBeanName(resource);

        if (ApplicationContextProvider.getBeanFactory().containsSingleton(beanName)) {
            unregisterConnector(beanName);
        }

        ApplicationContextProvider.getBeanFactory().registerSingleton(beanName, connector);
        LOG.debug("Successfully registered bean {}", beanName);
    }

    @Override
    public void unregisterConnector(final String id) {
        ApplicationContextProvider.getBeanFactory().destroySingleton(id);
    }

    @Transactional(readOnly = true)
    @Override
    public void load() {
        // This is needed in order to avoid encoding problems when sending error messages via REST
        CurrentLocale.set(Locale.ENGLISH);

        // Load all connector bundles
        connIdBundleManager.getConnManagers();

        // Load all resource-specific connectors
        int connectors = 0;
        for (ExternalResource resource : resourceDAO.findAll()) {
            LOG.info("Registering resource-connector pair {}-{}", resource, resource.getConnector());
            try {
                registerConnector(resource);
                connectors++;
            } catch (Exception e) {
                LOG.error("While registering resource-connector pair {}-{}", resource, resource.getConnector(), e);
            }
        }

        LOG.info("Done loading {} connectors", connectors);
    }

    @Transactional(readOnly = true)
    @Override
    public void unload() {
        int connectors = 0;
        for (ExternalResource resource : resourceDAO.findAll()) {
            final String beanName = getBeanName(resource);
            if (ApplicationContextProvider.getBeanFactory().containsSingleton(beanName)) {
                LOG.info("Unegistering resource-connector pair {}-{}", resource, resource.getConnector());
                unregisterConnector(beanName);
                connectors++;
            }
        }

        LOG.info("Done unloading {} connectors", connectors);

        ConnectorFacadeFactory.getInstance().dispose();
        connIdBundleManager.resetConnManagers();
        LOG.info("All connector resources disposed");
    }
}