package org.apache.camel.component.google.gcm.producer;

import static org.apache.camel.component.google.gcm.producer.constants.Util.nonNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.google.gcm.GCMEndpoint;
import org.apache.camel.component.google.gcm.model.GCMBody;
import org.apache.camel.component.google.gcm.model.GCMNotification;
import org.apache.camel.component.google.gcm.producer.constants.CamelHeaderConstants;
import org.apache.camel.component.google.gcm.producer.http.RetrySender;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by miki on 14.04.2015.
 * <p/>
 * The GoogleCloudMessaging producer.
 * <p>
 * GCM provides 2 connections servers: HTTP and XMPP (CCS)
 * Both: up to 4KB of data;
 * HTTP: downstream cloud - to - device
 * ----- synchronous, POST request with JSON or Plain Text;
 * ----- MultiCast supported in JSON
 * XMPP: upstream (device-to-cloud) and downstream (cloud-to-device)
 * ----- Asynchronous. 3rd party APP servers send/receive messages to /from all their devices over persistent XMPP
 * ----- XMPP sends acknowledgement or failure notifications in the form of special ACK and NACK JSON encoded XMPP
 * ----- messages asynchronously
 * ----- JSON only; MultiCast not supported
 * </p>
 */

public abstract class GCMProducer extends DefaultProducer implements IGCMProducer {
    /**
     *
     */
    private static final Logger LOG = LoggerFactory.getLogger(GCMProducer.class);
    
    
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * APIKey used only once in an endpoint; Wanna do it twice? Use another endpoint
     */
    protected final String apiKey;
    /**
     * The endpoint;
     */
    protected final GCMEndpoint endpoint;

    /**
     * @param endpoint
     * @param apiKey   from google
     */
    public GCMProducer(GCMEndpoint endpoint, final String apiKey) {
        super(endpoint);
        this.endpoint = endpoint;
        this.apiKey = nonNull(apiKey);
    }

    public void process(Exchange exchange) throws Exception {
        //decide if we send multi cast or not. also decide if we use the retrydecorator;
        Message camelMessage = nonNull(exchange.getIn());
        GCMBody message = this.getMessageFromBodyOrHeaders(camelMessage);

        Set<String> recipients = fetchAllDeviceIdsFromHeaders(camelMessage);
        IGCMProducer retryStrategy = getRetryStrategy(camelMessage);
        if (recipients.size() == 1) {
            retryStrategy.send(message, recipients.iterator().next());
        } else {
            retryStrategy.send(message, recipients);
        }
    }

    private IGCMProducer getRetryStrategy(Message camelMessage) {
        int retries = 0;
        if (camelMessage.getHeader(CamelHeaderConstants.RETRIES) != null) {
            try {
                retries = Integer.valueOf(String.valueOf(camelMessage.getHeader(CamelHeaderConstants.RETRIES)));
            } catch (NumberFormatException e) {
                LOG.warn("invalid number of retries {}, error : {}",
                        camelMessage.getHeader(CamelHeaderConstants.RETRIES), e);
            }
        }

        if (retries == 0) {
            return this;
        } else {
            return new RetrySender(this, retries);
        }
    }

    /**
     * @param camelMessage
     * @return
     */
    private GCMBody getMessageFromBodyOrHeaders(Message camelMessage) {
        GCMBody message;
        if (camelMessage.getBody() != null && camelMessage.getBody() instanceof GCMBody) {
            //cool we already have the GCM Message in the body;
            message = (GCMBody) camelMessage.getBody();
        } else {
            //we have to build it from headers and body;
            message = getMessageFromHeaders(camelMessage);
        }
        return message;
    }

    /**
     * Headers have priority over whatever is configured in the endpoint;
     *
     * @param camelMessage the message from camel
     * @return message to be sent to GCM;
     */
    @SuppressWarnings("unchecked")
    private GCMBody getMessageFromHeaders(Message camelMessage) {
        GCMBody.Builder builder = new GCMBody.Builder();
        Object payLoad = camelMessage.getBody();
        if (payLoad != null) {
            if (payLoad instanceof Map) {
                //if it's not a map there is nothing I can do with it
                builder.addData((Map<String, String>) payLoad);
            } else {
                try {
                //let's make a JSON
                builder.addData(CamelHeaderConstants.DATA, JSON_MAPPER.writeValueAsString(payLoad));
                }
                catch(JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
            }
        }

        builder
                .collapseKey(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.COLLAPSE_KEY,
                        String.class, endpoint.getConfiguration().getCollapseKey()))
                .delayWhileIdle(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.DELAY_WHILE_IDLE,
                        Boolean.class, endpoint.getConfiguration().isDelayWhileIdle()))
                .dryRun(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.DRY_RUN, Boolean.class,
                        endpoint.getConfiguration().isDryRun()))
                .restrictedPackageName(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.RESTRICTED_PACKAGE_NAME,
                        String.class, endpoint.getConfiguration().getRestrictedPackageName()))
                .timeToLive(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.TIME_TO_LIVE,
                        Integer.class, endpoint.getConfiguration().getTimeToLive()));
        
        
        //Building the GCM notification object
        GCMNotification notification = getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION,GCMNotification.class, null);
        if(notification == null) {

          GCMNotification.Builder notificationBuilder = new  GCMNotification.Builder();
          notificationBuilder
          .badge(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_BADGE,
                        String.class, null))
          .body(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_BODY,
            String.class, null))            
          .bodyLocArgs(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_BODY_LOC_ARGS,
                        List.class, null))
          .bodyLocKey(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_BODY_LOC_KEY,
                        String.class, null))
          .clickAction(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_CLICK_ACTION,
                        String.class, null))
          .color(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_COLOR,
                        String.class, null))
          .icon(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_ICON,
                        String.class, null))
          .sound(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_SOUND,
                        String.class, null))
          .tag(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_TAG,
                        String.class, null))
          .title(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_TITLE,
                        String.class, null))
          .titleLocArgs(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_TITLE_LOC_ARGS,
                        List.class, null))
          .titleLocKey(getParameterFromHeaderOrConfig(camelMessage, CamelHeaderConstants.NOTIFICATION_TITLE_LOC_KEY,
                        String.class, null));
          
          builder.notification(notificationBuilder.build());
        }
        else {          
          builder.notification(notification);          
        }
        
        return builder.build();
    }

    private <T> T getParameterFromHeaderOrConfig(Message camelMessage, String paramKey, Class<T> expectedClass,
                                                 T defaultValue) {
        Object fromHeader = camelMessage.getHeader(paramKey);
        if (fromHeader != null && expectedClass.isInstance(fromHeader)) {
            return (T) fromHeader;
        }
        return defaultValue;
    }


    private Set<String> fetchAllDeviceIdsFromHeaders(Message message) throws IllegalArgumentException {
        Set<String> registerIds = new HashSet<>();
        Object recipients = nonNull(message.getHeader(CamelHeaderConstants.TO));
        //it can either be a collection or a simple string for one user;
        if (recipients instanceof Collection) {
            registerIds.addAll((Collection<? extends String>) recipients);
        } else if (recipients instanceof String) {
            registerIds.add(recipients.toString());
        }

        if (registerIds.isEmpty()) {
            throw new IllegalArgumentException("A collection or a single device regID is missing from headers");
        }

        return registerIds;
    }

    protected static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.warn("IOException closing stream", e);
            }
        }
    }

}
