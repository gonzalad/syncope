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
package org.apache.syncope.client.console.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

/**
 *
 * @author agonzalez
 */
public class StaticListImplementationLookup extends ImplementationLookupSupport {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(StaticListImplementationLookup.class);

    private List<String> classNames = Arrays.asList(
            "org.apache.syncope.client.console.wicket.markup.html.form.preview.BinaryCertPreviewer",
            "org.apache.syncope.client.console.wicket.markup.html.form.preview.BinaryImagePreviewer",
            "org.apache.syncope.client.console.wicket.markup.html.form.preview.BinaryPDFPreviewer",
            "org.apache.syncope.client.console.wicket.markup.html.form.preview.DefaultPreviewer",
            "org.apache.syncope.client.console.pages.CamelRoutes",
            "org.apache.syncope.client.console.widgets.CamelMetricsWidget");

    @Override
    protected List<Class<?>> scan() {
        List<Class<?>> scannedClasses = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className, true, ClassUtils.getDefaultClassLoader());
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
