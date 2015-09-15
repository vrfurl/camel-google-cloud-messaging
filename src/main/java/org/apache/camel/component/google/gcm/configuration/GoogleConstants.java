package org.apache.camel.component.google.gcm.configuration;

/**
 * Created by miki on 02.04.2015.
 */
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

/**
 * Constants used on GCM service communication.
 */
public final class GoogleConstants {

    /**
     * Endpoint for sending messages.
     */
    public static final String GCM_SEND_ENDPOINT =
            "https://gcm-http.googleapis.com/gcm/send";

    public static final String UTF8 = "UTF-8";

    /**
     * Initial delay before first retry, without jitter.
     */
    protected static final int BACKOFF_INITIAL_DELAY = 1000;
    /**
     * Maximum delay before a retry.
     */
    protected static final int MAX_BACKOFF_DELAY = 1024000;

    /**
     * HTTP parameter for registration id.
     */
    public static final String PARAM_REGISTRATION_ID = "to";

    /**
     * HTTP parameter for collapse key.
     */
    public static final String PARAM_COLLAPSE_KEY = "collapse_key";

    /**
     * HTTP parameter for delaying the message delivery if the device is idle.
     */
    public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

    /**
     * HTTP parameter for telling gcm to validate the message without actually sending it.
     */
    public static final String PARAM_DRY_RUN = "dry_run";

    /**
     * HTTP parameter for package name that can be used to restrict message delivery by matching
     * against the package name used to generate the registration id.
     */
    public static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";

    /**
     * Prefix to HTTP parameter used to pass key-values in the message payload.
     */
    public static final String PARAM_PAYLOAD_PREFIX = "data.";

    /**
     * Prefix to HTTP parameter used to set the message time-to-live.
     */
    public static final String PARAM_TIME_TO_LIVE = "time_to_live";
    
    
    
    
    /**
     * Too many messages sent by the sender. Retry after a while.
     */
    public static final String ERROR_QUOTA_EXCEEDED = "QuotaExceeded";

    /**
     * Too many messages sent by the sender to a specific device.
     * Retry after a while.
     */
    public static final String ERROR_DEVICE_QUOTA_EXCEEDED =
            "DeviceQuotaExceeded";

    /**
     * Missing registration_id.
     * Sender should always add the registration_id to the request.
     */
    public static final String ERROR_MISSING_REGISTRATION = "MissingRegistration";

    /**
     * Bad registration_id. Sender should remove this registration_id.
     */
    public static final String ERROR_INVALID_REGISTRATION = "InvalidRegistration";

    /**
     * The sender_id contained in the registration_id does not match the
     * sender_id used to register with the GCM servers.
     */
    public static final String ERROR_MISMATCH_SENDER_ID = "MismatchSenderId";

    /**
     * The user has uninstalled the application or turned off notifications.
     * Sender should stop sending messages to this device and delete the
     * registration_id. The oldclient needs to re-register with the GCM servers to
     * receive notifications again.
     */
    public static final String ERROR_NOT_REGISTERED = "NotRegistered";

    /**
     * The payload of the message is too big, see the limitations.
     * Reduce the size of the message.
     */
    public static final String ERROR_MESSAGE_TOO_BIG = "MessageTooBig";

    /**
     * Collapse key is required. Include collapse key in the request.
     */
    public static final String ERROR_MISSING_COLLAPSE_KEY = "MissingCollapseKey";

    /**
     * A particular message could not be sent because the GCM servers were not
     * available. Used only on JSON requests, as in plain text requests
     * unavailability is indicated by a 503 response.
     */
    public static final String ERROR_UNAVAILABLE = "Unavailable";

    /**
     * A particular message could not be sent because the GCM servers encountered
     * an error. Used only on JSON requests, as in plain text requests internal
     * errors are indicated by a 500 response.
     */
    public static final String ERROR_INTERNAL_SERVER_ERROR =
            "InternalServerError";

    /**
     * Time to Live value passed is less than zero or more than maximum.
     */
    public static final String ERROR_INVALID_TTL = "InvalidTtl";

    /**
     * Token returned by GCM when a message was successfully sent.
     */
    public static final String TOKEN_MESSAGE_ID = "id";

    /**
     * Token returned by GCM when the requested registration id has a canonical
     * value.
     */
    public static final String TOKEN_CANONICAL_REG_ID = "registration_id";

    /**
     * Token returned by GCM when there was an error sending a message.
     */
    public static final String TOKEN_ERROR = "Error";

    /**
     * JSON-only field representing the registration ids.
     */
    public static final String JSON_REGISTRATION_IDS = "registration_ids";
    
    /**
     * JSON-only for registration id.
     */
    public static final String JSON_REGISTRATION_ID = "to";


    /**
     * JSON-only field representing the payload data.
     */
    public static final String JSON_PAYLOAD = "data";
    
    
    
    //Begin Notification JSON object
    
    /**
     *  this parameter specifies the predefined, user-visible key-value pairs of the notification payload.
     */
    public static final String JSON_NOTIFICATION = "notification";

    
    /**
     * JSON-only indicates notification title. This field is not visible on iOS phones and tablets.
     */
    public static final String JSON_NOTIFICATION_TITLE = "title";
    
    /**
     * JSON-only indicates notification body text.
     */
    public static final String JSON_NOTIFICATION_BODY = "body";
    
    /**
     * JSON-only indicates notification icon. On Android: sets value to myicon for drawable resource myicon.
     */
    public static final String JSON_NOTIFICATION_ICON = "icon";
    
    /**
     * JSON-only indicates sound to be played. Supports only default currently.
     */
    public static final String JSON_NOTIFICATION_SOUND = "sound";
    
    /**
     * JSON-only indicates the badge on client app home icon.
     */
    public static final String JSON_NOTIFICATION_BADGE = "badge";
    
    /**
     * JSON-only indicates whether each notification message results in a new entry on the notification center on Android. 
     * If not set, each request creates a new notification. 
     * If set, and a notification with the same tag is already being shown, 
     * the new notification replaces the existing one in notification center.
     */
    public static final String JSON_NOTIFICATION_TAG = "tag";
    
    /**
     * JSON-only indicates color of the icon, expressed in #rrggbb format
     */
    public static final String JSON_NOTIFICATION_COLOR = "color";
    
    /**
     * JSON-only indicates the action associated with a user click on the notification.
     */
    public static final String JSON_NOTIFICATION_CLICK_ACTION = "click_action";
    
    /**
     * JSON-only indicates the key to the body string for localization.
     */
    public static final String JSON_NOTIFICATION_BODY_LOC_KEY = "body_loc_key";
    
    /**
     * JSON-only indicates the string value to replace format specifiers in body string for localization.
     */
    public static final String JSON_NOTIFICATION_BODY_LOC_ARGS = "body_loc_args";
    
    /**
     * JSON-only indicates the key to the title string for localization.
     */
    public static final String JSON_NOTIFICATION_TITLE_LOC_KEY = "title_loc_key";
    
    /**
     * JSON-only indicates the string value to replace format specifiers in title string for localization. 
     */
    public static final String JSON_NOTIFICATION_TITLE_LOC_ARGS = "title_loc_args";

    /**
     * JSON-only field representing the number of successful messages.
     */
    public static final String JSON_SUCCESS = "success";

    /**
     * JSON-only field representing the number of failed messages.
     */
    public static final String JSON_FAILURE = "failure";

    /**
     * JSON-only field representing the number of messages with a canonical
     * registration id.
     */
    public static final String JSON_CANONICAL_IDS = "canonical_ids";

    /**
     * JSON-only field representing the id of the multicast request.
     */
    public static final String JSON_MULTICAST_ID = "multicast_id";

    /**
     * JSON-only field representing the result of each individual request.
     */
    public static final String JSON_RESULTS = "results";

    /**
     * JSON-only field representing the error field of an individual request.
     */
    public static final String JSON_ERROR = "error";

    /**
     * JSON-only field sent by GCM when a message was successfully sent.
     */
    public static final String JSON_MESSAGE_ID = "message_id";

    private GoogleConstants() {
        throw new UnsupportedOperationException();
    }

}