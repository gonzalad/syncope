/*
 * Copyright 2016 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.syncope.core.logic.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 *
 * @author gonzalad
 */
@Component
public class StaticListImplementationLookup extends ImplementationLookupSupport {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StaticListImplementationLookup.class);

    private List<String> classNames = Arrays.asList("org.apache.syncope.fit.core.reference.DoubleValueLogicActions",
            "org.apache.syncope.fit.core.reference.PrefixMappingItemTransformer",
            "org.apache.syncope.fit.core.reference.TestAccountRule",
            "org.apache.syncope.fit.core.reference.TestNotificationRecipientsProvider",
            "org.apache.syncope.fit.core.reference.TestPasswordRule",
            "org.apache.syncope.fit.core.reference.TestPullActions",
            "org.apache.syncope.fit.core.reference.TestPullRule",
            "org.apache.syncope.fit.core.reference.TestReconciliationFilterBuilder",
            "org.apache.syncope.fit.core.reference.TestSampleJobDelegate",
            "org.apache.syncope.core.logic.report.GroupReportlet",
            "org.apache.syncope.core.logic.report.UserReportlet",
            "org.apache.syncope.core.logic.report.AuditReportlet",
            "org.apache.syncope.core.logic.report.ReconciliationReportlet",
            "org.apache.syncope.core.logic.report.StaticReportlet",
            "org.apache.syncope.core.migration.MigrationPullActions",
            "org.apache.syncope.core.persistence.jpa.dao.DefaultPasswordRule",
            "org.apache.syncope.core.persistence.jpa.dao.DefaultAccountRule",
            "org.apache.syncope.core.persistence.jpa.attrvalue.validation.BasicValidator",
            "org.apache.syncope.core.persistence.jpa.attrvalue.validation.EmailAddressValidator",
            "org.apache.syncope.core.persistence.jpa.attrvalue.validation.AlwaysTrueValidator",
            "org.apache.syncope.core.provisioning.java.pushpull.PushJobDelegate",
            "org.apache.syncope.core.provisioning.java.pushpull.PullJobDelegate",
            "org.apache.syncope.core.provisioning.java.pushpull.LDAPPasswordPullActions",
            "org.apache.syncope.core.provisioning.java.pushpull.DBPasswordPullActions",
            "org.apache.syncope.core.provisioning.java.pushpull.LDAPMembershipPullActions",
            "org.apache.syncope.core.provisioning.java.data.JEXLMappingItemTransformerImpl",
            "org.apache.syncope.core.provisioning.java.propagation.DBPasswordPropagationActions",
            "org.apache.syncope.core.provisioning.java.pushpull.PlainAttrsPullCorrelationRule",
            "org.apache.syncope.core.provisioning.java.data.DefaultMappingItemTransformer",
            "org.apache.syncope.core.provisioning.java.propagation.LDAPMembershipPropagationActions",
            "org.apache.syncope.core.provisioning.java.propagation.LDAPPasswordPropagationActions",
            "org.apache.syncope.core.provisioning.java.DefaultLogicActions",
            "org.apache.syncope.core.provisioning.java.job.GroupMemberProvisionTaskJobDelegate");

    @Override
    protected List<Class<?>> scan() {
        List<Class<?>> scannedClasses = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className, false, ClassUtils.getDefaultClassLoader());
                scannedClasses.add(clazz);
            } catch (Throwable t) {
                LOG.warn("Could not load class {}", className, t);
            }
        }
        return scannedClasses;
    }

    public void setClassNames(final List<String> classNames) {
        this.classNames = classNames;
    }
}
