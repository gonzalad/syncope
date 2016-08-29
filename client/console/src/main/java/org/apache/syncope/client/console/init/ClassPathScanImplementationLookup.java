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
package org.apache.syncope.client.console.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.util.ClassUtils;

public class ClassPathScanImplementationLookup extends ImplementationLookupSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ClassPathScanImplementationLookup.class);
    
    private List<String> basePackages = Arrays.asList("org.apache.syncope.client.console");

    @Override
    public List<Class<?>> scan() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
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
