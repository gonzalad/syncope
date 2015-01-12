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
package org.apache.syncope.common.lib.types;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum EntityViolationType {

    Standard(""),
    InvalidAccountPolicy("org.apache.syncope.server.persistence.validation.accountpolicy"),
    InvalidConnInstanceLocation("org.apache.syncope.server.persistence.validation.conninstance.location"),
    InvalidConnPoolConf("org.apache.syncope.server.persistence.validation.conninstance.poolConf"),
    InvalidCPlainSchema("org.apache.syncope.server.persistence.validation.attrvalue.cPlainSchema"),
    InvalidMapping("org.apache.syncope.server.persistence.validation.mapping"),
    InvalidMPlainSchema("org.apache.syncope.server.persistence.validation.attrvalue.mPlainSchema"),
    InvalidMDerSchema("org.apache.syncope.server.persistence.validation.attrvalue.mDerSchema"),
    InvalidMVirSchema("org.apache.syncope.server.persistence.validation.attrvalue.mVirSchema"),
    InvalidName("org.apache.syncope.server.persistence.validation.name"),
    InvalidNotification("org.apache.syncope.server.persistence.validation.notification"),
    InvalidPassword("org.apache.syncope.server.persistence.validation.syncopeuser.password"),
    InvalidPasswordPolicy("org.apache.syncope.server.persistence.validation.passwordpolicy"),
    InvalidPolicy("org.apache.syncope.server.persistence.validation.policy"),
    InvalidPropagationTask("org.apache.syncope.server.persistence.validation.propagationtask"),
    InvalidRPlainSchema("org.apache.syncope.server.persistence.validation.attrvalue.rPlainSchema"),
    InvalidRDerSchema("org.apache.syncope.server.persistence.validation.attrvalue.rDerSchema"),
    InvalidRVirSchema("org.apache.syncope.server.persistence.validation.attrvalue.rVirSchema"),
    InvalidReport("org.apache.syncope.server.persistence.validation.report"),
    InvalidResource("org.apache.syncope.server.persistence.validation.externalresource"),
    InvalidRoleOwner("org.apache.syncope.server.persistence.validation.syncoperole.owner"),
    InvalidSchemaEncrypted("org.apache.syncope.server.persistence.validation.schema.encrypted"),
    InvalidSchemaEnum("org.apache.syncope.server.persistence.validation.schema.enum"),
    InvalidSchemaMultivalueUnique("org.apache.syncope.server.persistence.validation.schema.multivalueUnique"),
    InvalidSchedTask("org.apache.syncope.server.persistence.validation.schedtask"),
    InvalidSyncTask("org.apache.syncope.server.persistence.validation.synctask"),
    InvalidSyncPolicy("org.apache.syncope.server.persistence.validation.syncpolicy"),
    InvalidUPlainSchema("org.apache.syncope.server.persistence.validation.attrvalue.uPlainSchema"),
    InvalidUDerSchema("org.apache.syncope.server.persistence.validation.attrvalue.derSchema"),
    InvalidUVirSchema("org.apache.syncope.server.persistence.validation.attrvalue.uVirSchema"),
    InvalidUsername("org.apache.syncope.server.persistence.validation.syncopeuser.username"),
    InvalidValueList("org.apache.syncope.server.persistence.validation.attr.valueList"),
    MoreThanOneNonNull("org.apache.syncope.server.persistence.validation.attrvalue.moreThanOneNonNull");

    private String message;

    EntityViolationType(final String message) {
        this.message = message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}