package org.apache.camel.component.google.gcm.model;

import java.util.List;

/**
 * GCM notification object {@link https
 * ://developers.google.com/cloud-messaging/http-server-ref#notification-payload-support}.
 * <p/>
 * <p/>
 * Instances of this class are immutable and should be created using a {@link Builder}. Examples:
 * <p/>
 * <strong>Simplest notification:</strong>
 * 
 * <pre><code>
 * GCMNotification notification = new GCMNotification.Builder().build();
 * </pre>
 * </code>
 * <p/>
 * <strong>GCMBody with optional attributes:</strong>
 * 
 * <pre><code>
 * GCMBody message = new GCMBody.Builder()
 *    .collapseKey(collapseKey)
 *    .timeToLive(3)
 *    .delayWhileIdle(true)
 *    .dryRun(true)
 *    .restrictedPackageName(restrictedPackageName)
 *    .build();
 * </pre>
 * </code>
 * <p/>
 * <strong>GCMBody with optional attributes and payload data:</strong>
 * 
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
 * </pre>
 * </code>
 */
public class GCMNotification {


  private final String title;
  private final String body;
  private final String icon;
  private final String sound;
  private final String badge;
  private final String tag;
  private final String color;
  private final String clickAction;
  private final String bodyLocKey;
  private final List<String> bodyLocArgs;
  private final String titleLocKey;
  private final List<String> titleLocArgs;



  public GCMNotification(Builder builder) {

    this.title = builder.title;
    this.body = builder.body;
    this.icon = builder.icon;
    this.sound = builder.sound;
    this.badge = builder.badge;
    this.tag = builder.tag;
    this.color = builder.color;
    this.clickAction = builder.clickAction;
    this.bodyLocKey = builder.bodyLocKey;
    this.bodyLocArgs = builder.bodyLocArgs;
    this.titleLocKey = builder.titleLocKey;
    this.titleLocArgs = builder.titleLocArgs;

  }

  public static final class Builder {

    private String title;
    private String body;
    private String icon;
    private String sound;
    private String badge;
    private String tag;
    private String color;
    private String clickAction;
    private String bodyLocKey;
    private List<String> bodyLocArgs;
    private String titleLocKey;
    private List<String> titleLocArgs;
    
    public Builder() {
      
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Builder icon(String icon) {
      this.icon = icon;
      return this;
    }

    public Builder sound(String sound) {
      this.sound = sound;
      return this;
    }

    public Builder badge(String badge) {
      this.badge = badge;
      return this;
    }

    public Builder tag(String tag) {
      this.tag = tag;
      return this;
    }

    public Builder color(String color) {
      this.color = color;
      return this;
    }

    public Builder clickAction(String clickAction) {
      this.clickAction = clickAction;
      return this;
    }

    public Builder bodyLocKey(String bodyLocKey) {
      this.bodyLocKey = bodyLocKey;
      return this;
    }

    public Builder bodyLocArgs(List<String> bodyLocArgs) {
      this.bodyLocArgs = bodyLocArgs;
      return this;
    }

    public Builder titleLocKey(String titleLocKey) {
      this.titleLocKey = titleLocKey;
      return this;
    }

    public Builder titleLocArgs(List<String> titleLocArgs) {
      this.titleLocArgs = titleLocArgs;
      return this;
    }

    public GCMNotification build() {
      return new GCMNotification(this);
    }


  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public String getIcon() {
    return icon;
  }

  public String getSound() {
    return sound;
  }

  public String getBadge() {
    return badge;
  }

  public String getTag() {
    return tag;
  }

  public String getColor() {
    return color;
  }

  public String getClickAction() {
    return clickAction;
  }

  public String getBodyLocKey() {
    return bodyLocKey;
  }

  public List<String> getBodyLocArgs() {
    return bodyLocArgs;
  }

  public String getTitleLocKey() {
    return titleLocKey;
  }

  public List<String> getTitleLocArgs() {
    return titleLocArgs;
  }



}
