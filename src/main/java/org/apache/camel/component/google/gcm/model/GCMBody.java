package org.apache.camel.component.google.gcm.model;
/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * GCM message.
 * <p/>
 * <p/>
 * Instances of this class are immutable and should be created using a
 * {@link Builder}. Examples:
 * <p/>
 * <strong>Simplest message:</strong>
 * <pre><code>
 * GCMBody message = new GCMBody.Builder().build();
 * </pre></code>
 * <p/>
 * <strong>GCMBody with optional attributes:</strong>
 * <pre><code>
 * GCMBody message = new GCMBody.Builder()
 *    .collapseKey(collapseKey)
 *    .timeToLive(3)
 *    .delayWhileIdle(true)
 *    .dryRun(true)
 *    .restrictedPackageName(restrictedPackageName)
 *    .build();
 * </pre></code>
 * <p/>
 * <strong>GCMBody with optional attributes and payload data:</strong>
 * <pre><code>
 * GCMBody message = new GCMBody.Builder()
 *    .collapseKey(collapseKey)
 *    .timeToLive(3)
 *    .delayWhileIdle(true)
 *    .dryRun(true)
 *    .restrictedPackageName(restrictedPackageName)
 *    .addData("key1", "value1")
 *    .addData("key2", "value2")
 *    .build();
 * </pre></code>
 */
public final class GCMBody implements Serializable {

    private final String collapseKey;
    private final Boolean delayWhileIdle;
    private final Integer timeToLive;
    private final Map<String, String> data;
    private final Boolean dryRun;
    private final String restrictedPackageName;
    private final GCMNotification notification;

    public static final class Builder {

        private final Map<String, String> data;

        // optional parameters
        private String collapseKey;
        private Boolean delayWhileIdle;
        private Integer timeToLive;
        private Boolean dryRun;
        private String restrictedPackageName;
        private GCMNotification notification;

        public Builder() {
            this.data = new LinkedHashMap<String, String>();
        }

        /**
         * Sets the collapseKey property.
         */
        public Builder collapseKey(String value) {
            collapseKey = value;
            return this;
        }

        /**
         * Sets the delayWhileIdle property (default value is {@literal false}).
         */
        public Builder delayWhileIdle(Boolean value) {
            delayWhileIdle = value;
            return this;
        }

        /**
         * Sets the time to live, in seconds.
         */
        public Builder timeToLive(Integer value) {
            timeToLive = value;
            return this;
        }

        /**
         * Adds a key/value pair to the payload data.
         */
        public Builder addData(String key, String value) {
            data.put(key, value);
            return this;
        }

        /**
         * adds a map to the payload data;
         *
         * @param data
         * @return
         */
        public Builder addData(Map<String, String> data) {
            this.data.putAll(data);
            return this;
        }

        /**
         * Sets the dryRun property (default value is {@literal false}).
         */
        public Builder dryRun(Boolean value) {
            dryRun = value;
            return this;
        }

        /**
         * Sets the restrictedPackageName property.
         */
        public Builder restrictedPackageName(String value) {
            restrictedPackageName = value;
            return this;
        }
        
        
        public Builder notification(GCMNotification value) {
          notification = value;
          return this;
        }

        public GCMBody build() {
            return new GCMBody(this);
        }

    }

    private GCMBody(Builder builder) {
        collapseKey = builder.collapseKey;
        delayWhileIdle = builder.delayWhileIdle;
        data = Collections.unmodifiableMap(builder.data);
        timeToLive = builder.timeToLive;
        dryRun = builder.dryRun;
        restrictedPackageName = builder.restrictedPackageName;
        notification =  builder.notification;
    }

    /**
     * Gets the collapse key.
     */
    public String getCollapseKey() {
        return collapseKey;
    }

    /**
     * Gets the delayWhileIdle flag.
     */
    public Boolean isDelayWhileIdle() {
        return delayWhileIdle;
    }

    /**
     * Gets the time to live (in seconds).
     */
    public Integer getTimeToLive() {
        return timeToLive;
    }

    /**
     * Gets the dryRun flag.
     */
    public Boolean isDryRun() {
        return dryRun;
    }

    /**
     * Gets the restricted package name.
     */
    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    /**
     * Gets the payload data, which is immutable.
     */
    public Map<String, String> getData() {
        return data;
    }
    
    
    public GCMNotification getNotification() {
      return notification;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("GCMBody(");
        if (collapseKey != null) {
            builder.append("collapseKey=").append(collapseKey).append(", ");
        }
        if (timeToLive != null) {
            builder.append("timeToLive=").append(timeToLive).append(", ");
        }
        if (delayWhileIdle != null) {
            builder.append("delayWhileIdle=").append(delayWhileIdle).append(", ");
        }
        if (dryRun != null) {
            builder.append("dryRun=").append(dryRun).append(", ");
        }
        if (restrictedPackageName != null) {
            builder.append("restrictedPackageName=").append(restrictedPackageName).append(", ");
        }
        if (!data.isEmpty()) {
            builder.append("data: {");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue())
                        .append(",");
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append("}");
        }
        if (builder.charAt(builder.length() - 1) == ' ') {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")");
        return builder.toString();
    }

}
