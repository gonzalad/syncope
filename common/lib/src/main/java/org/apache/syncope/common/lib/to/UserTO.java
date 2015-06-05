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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name = "user")
@XmlType
public class UserTO extends AnyTO {

    private static final long serialVersionUID = 7791304495192615740L;

    private String password;

    private final List<Long> roles = new ArrayList<>();

    private final List<Long> dynRoles = new ArrayList<>();

    private String token;

    private Date tokenExpireTime;

    private String username;

    private Date lastLoginDate;

    private Date changePwdDate;

    private Integer failedLogins;

    private Long securityQuestion;

    private String securityAnswer;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    @JsonProperty("roles")
    public List<Long> getRoles() {
        return roles;
    }

    @XmlElementWrapper(name = "dynRoles")
    @XmlElement(name = "role")
    @JsonProperty("dynRoles")
    public List<Long> getDynRoles() {
        return dynRoles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Date getTokenExpireTime() {
        return tokenExpireTime == null
                ? null
                : new Date(tokenExpireTime.getTime());
    }

    public void setTokenExpireTime(final Date tokenExpireTime) {
        if (tokenExpireTime != null) {
            this.tokenExpireTime = new Date(tokenExpireTime.getTime());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Date getChangePwdDate() {
        return changePwdDate;
    }

    public Integer getFailedLogins() {
        return failedLogins;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setChangePwdDate(final Date changePwdDate) {
        this.changePwdDate = changePwdDate;
    }

    public void setFailedLogins(final Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    public void setLastLoginDate(final Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(final Long securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(final String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE) {

            @Override
            protected boolean accept(final Field f) {
                return super.accept(f) && !f.getName().equals("password");
            }
        }.toString();
    }
}