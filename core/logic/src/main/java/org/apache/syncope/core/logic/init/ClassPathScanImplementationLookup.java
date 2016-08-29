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
package org.apache.syncope.core.logic.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.syncope.common.lib.policy.AccountRuleConf;
import org.apache.syncope.common.lib.policy.PasswordRuleConf;
import org.apache.syncope.common.lib.report.ReportletConf;
import org.apache.syncope.core.persistence.api.dao.Reportlet;
import org.apache.syncope.core.persistence.api.ImplementationLookup;
import org.apache.syncope.core.persistence.api.attrvalue.validation.Validator;
import org.apache.syncope.core.persistence.api.dao.AccountRule;
import org.apache.syncope.core.persistence.api.dao.PasswordRule;
import org.apache.syncope.core.provisioning.api.LogicActions;
import org.apache.syncope.core.provisioning.api.data.MappingItemTransformer;
import org.apache.syncope.core.provisioning.api.job.SchedTaskJobDelegate;
import org.apache.syncope.core.provisioning.api.notification.NotificationRecipientsProvider;
import org.apache.syncope.core.provisioning.api.propagation.PropagationActions;
import org.apache.syncope.core.provisioning.api.pushpull.PushActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;
import org.apache.syncope.core.provisioning.api.pushpull.ReconciliationFilterBuilder;
import org.apache.syncope.core.provisioning.api.pushpull.PullCorrelationRule;
import org.apache.syncope.core.provisioning.api.pushpull.PullActions;

/**
 * Cache class names for all implementations of Syncope interfaces found in classpath, for later usage.
 */
public class ClassPathScanImplementationLookup extends ImplementationLookupSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ImplementationLookup.class);

    private Map<Type, Set<String>> classNames;

    private Map<Class<? extends ReportletConf>, Class<? extends Reportlet>> reportletClasses;

    private Map<Class<? extends AccountRuleConf>, Class<? extends AccountRule>> accountRuleClasses;

    private Map<Class<? extends PasswordRuleConf>, Class<? extends PasswordRule>> passwordRuleClasses;
    
    private List<String> basePackages = Arrays.asList("org.apache.syncope.fit.core", "org.apache.syncope.core");

    @Override
    protected List<Class<?>> scan() {
        
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Reportlet.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(AccountRule.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(PasswordRule.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(MappingItemTransformer.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(SchedTaskJobDelegate.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(ReconciliationFilterBuilder.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(LogicActions.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(PropagationActions.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(PullActions.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(PushActions.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(PullCorrelationRule.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(Validator.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(NotificationRecipientsProvider.class));

        List<Class<?>> scannedClasses = new ArrayList<>();
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        for (String basePackage : getBasePackages()) {
            beanDefinitions.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition bd : beanDefinitions) {
            try {
                Class<?> clazz = ClassUtils.resolveClassName(
                        bd.getBeanClassName(), ClassUtils.getDefaultClassLoader());
                scannedClasses.add(clazz);
            } catch (Throwable t) {
                LOG.warn("Could not inspect class {}", bd.getBeanClassName(), t);
            }
        }
        return scannedClasses;
    }

    /**
     * @return basePackages for syncope class classpath scanning
     */
    protected List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(final List<String> basePackages) {
        this.basePackages = basePackages;
    }
}
