package org.apache.camel.component.google.gcm.producer.builders;

import org.apache.camel.component.google.gcm.model.GCMBody;
import org.apache.camel.component.google.gcm.producer.constants.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

import static org.apache.camel.component.google.gcm.configuration.GoogleConstants.*;

/**
 * Created by miki on 15.04.2015.
 */
public class JsonBuilder extends AbstractBuilder {

  private List<String> registrationIds;
  private Map<Object, Object> jsonRequest;

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();


  public JsonBuilder(GCMBody message, String registrationId) {
    super(message);

  }

  public JsonBuilder(GCMBody message, Set<String> registrationIds) {
    super(message);
    this.registrationIds = new ArrayList<String>(registrationIds);// because the simple json has
                                                                  // issues with sets;
    jsonRequest = new HashMap<>();
  }

  @Override
  public String build() {
    if (Util.nonNull(registrationIds).isEmpty()) {
      throw new IllegalArgumentException("registrationIds cannot be empty");
    }

    if (registrationIds.size() > 1) {

      jsonRequest.put(JSON_REGISTRATION_IDS, registrationIds);

    } else {

      jsonRequest.put(JSON_REGISTRATION_ID, registrationIds.get(0));

    }



    setJsonField(PARAM_TIME_TO_LIVE, this.message.getTimeToLive());
    setJsonField(PARAM_COLLAPSE_KEY, this.message.getCollapseKey());
    setJsonField(PARAM_RESTRICTED_PACKAGE_NAME, this.message.getRestrictedPackageName());
    setJsonField(PARAM_DELAY_WHILE_IDLE, this.message.isDelayWhileIdle());
    setJsonField(PARAM_DRY_RUN, this.message.isDryRun());

    addPayload();
    addNotification();
    // return JSONValue.toJSONString(jsonRequest);
    String json;
    try {
      json = JSON_MAPPER.writeValueAsString(jsonRequest);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return json;
  }

  protected JsonBuilder addPayload() {
    Map<String, String> payload = this.message.getData();
    if (!payload.isEmpty()) {
      jsonRequest.put(JSON_PAYLOAD, payload);
    }
    return this;
  }

  protected JsonBuilder addNotification() {

    if (this.message.getNotification() == null) {
      return this;
    }

    Map<Object, Object> notificationMap = new HashMap<Object, Object>();

    setJsonField(JSON_NOTIFICATION_TITLE, this.message.getNotification().getTitle(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_BODY, this.message.getNotification().getBody(), notificationMap);
    setJsonField(JSON_NOTIFICATION_ICON, this.message.getNotification().getIcon(), notificationMap);
    setJsonField(JSON_NOTIFICATION_SOUND, this.message.getNotification().getSound(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_BADGE, this.message.getNotification().getBadge(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_TAG, this.message.getNotification().getTag(), notificationMap);
    setJsonField(JSON_NOTIFICATION_COLOR, this.message.getNotification().getColor(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_CLICK_ACTION, this.message.getNotification().getClickAction(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_BODY_LOC_KEY, this.message.getNotification().getBodyLocKey(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_BODY_LOC_ARGS, this.message.getNotification().getBodyLocArgs(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_TITLE_LOC_KEY, this.message.getNotification().getTitleLocKey(),
        notificationMap);
    setJsonField(JSON_NOTIFICATION_TITLE_LOC_ARGS, this.message.getNotification().getTitleLocArgs(),
        notificationMap);

    setJsonField(JSON_NOTIFICATION, notificationMap);

    return this;

  }

  /**
   * Sets a JSON field, but only if the value is not {@literal null}.
   */
  private void setJsonField(String field, Object value) {
    setJsonField(field, value, jsonRequest);
  }

  private void setJsonField(String field, Object value, Map<Object, Object> destination) {
    if (value != null) {
      destination.put(field, value);
    }
  }

}
