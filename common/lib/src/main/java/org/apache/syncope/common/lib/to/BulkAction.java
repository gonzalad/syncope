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
package org.apache.syncope.common.lib.to;

import java.util.ArrayList;
import java.util.List;
import org.apache.syncope.common.lib.AbstractBaseBean;

public class BulkAction extends AbstractBaseBean {

    private static final long serialVersionUID = 1395353278878758961L;

    public enum Type {

        MUSTCHANGEPASSWORD,
        DELETE,
        REACTIVATE,
        SUSPEND,
        DRYRUN,
        EXECUTE

    }

    private Type type;

    private final List<String> targets = new ArrayList<>();

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public List<String> getTargets() {
        return targets;
    }
}
