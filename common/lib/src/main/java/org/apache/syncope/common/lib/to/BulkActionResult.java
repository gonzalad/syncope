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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.syncope.common.lib.AbstractBaseBean;

public class BulkActionResult extends AbstractBaseBean {

    private static final long serialVersionUID = 2868894178821778133L;

    public enum Status {

        // general bulk action result statuses
        SUCCESS,
        FAILURE,
        // specific propagation task execution statuses
        CREATED,
        NOT_ATTEMPTED;

    }

    public static class Single extends AbstractBaseBean {

        private static final long serialVersionUID = -2677679977955844506L;

        private final String key;

        private final Status status;

        @JsonCreator
        public Single(@JsonProperty("key") final String key, @JsonProperty("status") final Status status) {
            this.key = key;
            this.status = status;
        }

        public String getKey() {
            return key;
        }

        public Status getStatus() {
            return status;
        }
    }

    private final List<Single> results = new ArrayList<>();

    @JsonIgnore
    public boolean add(final String key, final Status status) {
        Single single = new Single(key, status);
        return results.contains(single) || results.add(single);
    }

    public List<Single> getResults() {
        return results;
    }

    @JsonIgnore
    public Map<String, Status> getResultMap() {
        Map<String, Status> result = new HashMap<>(results.size());
        for (Single single : results) {
            result.put(single.getKey(), single.getStatus());
        }

        return Collections.unmodifiableMap(result);
    }
}
