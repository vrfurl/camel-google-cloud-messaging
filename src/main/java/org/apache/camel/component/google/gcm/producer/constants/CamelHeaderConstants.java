package org.apache.camel.component.google.gcm.producer.constants;


/**
 * Constants used in camel message headers;
 * Created by miki on 16.04.2015.
 */
public abstract class CamelHeaderConstants {
    public final static String TO = "to";
    public final static String RETRIES = "retries";
    public final static String COLLAPSE_KEY = "collapseKey";
    public final static String DELAY_WHILE_IDLE = "delayWhileIdle";
    public final static String DRY_RUN = "dryRun";
    public final static String RESTRICTED_PACKAGE_NAME = "restrictedPackageName";
    public static final String TIME_TO_LIVE = "timeToLive";
    public static final String DATA = "data";
    
    //Notification Header Constants
    public final static String NOTIFICATION = "notification";
    public final static String NOTIFICATION_TITLE = "notification.title";
    public final static String NOTIFICATION_BODY = "notification.body";
    public final static String NOTIFICATION_ICON = "notification.icon";
    public final static String NOTIFICATION_SOUND = "notification.sound";
    public final static String NOTIFICATION_BADGE = "notification.badge";
    public final static String NOTIFICATION_TAG  = "notification.tag";
    public final static String NOTIFICATION_COLOR  = "notification.color";
    public final static String NOTIFICATION_CLICK_ACTION  = "notification.clickAction";
    public final static String NOTIFICATION_BODY_LOC_KEY  = "notification.bodyLocKey";
    public final static String NOTIFICATION_BODY_LOC_ARGS  = "notification.bodyLocArgs";
    public final static String NOTIFICATION_TITLE_LOC_KEY  = "notification.titleLocKey";
    public final static String NOTIFICATION_TITLE_LOC_ARGS  = "notification.titleLocArgs";

    
}
