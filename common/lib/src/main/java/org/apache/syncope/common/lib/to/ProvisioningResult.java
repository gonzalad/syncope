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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
import java.util.List;
import org.apache.syncope.common.lib.AbstractBaseBean;

public class ProvisioningResult<E extends EntityTO> extends AbstractBaseBean {

    private static final long serialVersionUID = 351317476398082746L;

    @JsonIgnore
    private E entity;

    private final List<PropagationStatus> propagationStatuses = new ArrayList<>();

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    @JsonProperty
    public E getEntity() {
        return entity;
    }

    public void setEntity(final E any) {
        this.entity = any;
    }

    public List<PropagationStatus> getPropagationStatuses() {
        return propagationStatuses;
    }

}
