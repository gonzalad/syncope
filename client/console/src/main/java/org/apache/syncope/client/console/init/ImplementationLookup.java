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

import java.util.List;
import org.apache.syncope.client.console.pages.BaseExtPage;
import org.apache.syncope.client.console.pages.BasePage;
import org.apache.syncope.client.console.wicket.markup.html.form.preview.AbstractBinaryPreviewer;
import org.apache.syncope.client.console.widgets.BaseExtWidget;

/**
 *
 * @author agonzalez
 */
public interface ImplementationLookup {

    Class<? extends AbstractBinaryPreviewer> getPreviewerClass(final String mimeType);

    List<Class<? extends BasePage>> getPageClasses();

    List<Class<? extends AbstractBinaryPreviewer>> getPreviewerClasses();

    List<Class<? extends BaseExtPage>> getExtPageClasses();

    List<Class<? extends BaseExtWidget>> getExtWidgetClasses();
}
