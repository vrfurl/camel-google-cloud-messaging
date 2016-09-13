package org.apache.camel.component.google.gcm.producer.http;

import static org.apache.camel.component.google.gcm.configuration.GoogleConstants.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.camel.component.google.gcm.GCMEndpoint;
import org.apache.camel.component.google.gcm.model.GCMBody;
import org.apache.camel.component.google.gcm.model.MultiCastResponse;
import org.apache.camel.component.google.gcm.producer.GCMProducer;
import org.apache.camel.component.google.gcm.producer.builders.JsonBuilder;
import org.apache.camel.component.google.gcm.producer.parsers.MultiCastParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by miki on 15.04.2015.
 */
public class SimpleSender extends GCMProducer {

  private static final Logger LOG = LoggerFactory.getLogger(SimpleSender.class);

  public SimpleSender(GCMEndpoint endpoint, String apiKey) {
    super(endpoint, apiKey);
  }

  /**
   * @param message to be pushed to GCM; (body of camel message ) ;
   * @param registrationIdList device id list where to send
   * @return response from google;
   */
  @Override
  public MultiCastResponse send(GCMBody message, Set<String> registrationIdList)
      throws IOException {

    // TODO REPLACE THIS JSONBUILDER WITH Jackson or a better JSON builder. This builer is very
    // primitive and will not suffice when a complex custom data object is needed to be sent to GCM
    String requestBody = new JsonBuilder(message, registrationIdList).build();
    LOG.info("GCM: sending a multicast with Request body: {}", requestBody);
    try {
      HttpURLConnection conn = post(GCM_SEND_ENDPOINT, "application/json", requestBody);
      MultiCastParser parser = new MultiCastParser();
      String response = parser.getResponseFromConnection(conn);
      LOG.info("GCM: Multicast received response {}", response);
      return parser.parseResponseString(response);
    } catch (IOException e) {
      LOG.error("Exception posting to GCM", e);
      throw e;
    }
  }

  /**
   * @param message to be pushed to GCM; (body of camel message ) ;
   * @param regId device id where to send the message;
   * @return response from google, including errors;
   */
  @Override
  public MultiCastResponse send(GCMBody message, String regId) throws IOException {
    Set<String> regIds = new HashSet<String>();
    regIds.add(regId);
    String requestBody = new JsonBuilder(message, regIds).build();
    LOG.info("GCM: sending a POST with Request body: {}", requestBody);
    try {
      HttpURLConnection conn = post(GCM_SEND_ENDPOINT, "application/json", requestBody);
      MultiCastParser parser = new MultiCastParser();
      String response = parser.getResponseFromConnection(conn);
      LOG.info("GCM: Post received response {}", response);
      return parser.parseResponseString(response);
    } catch (IOException e) {
      LOG.error("Exception posting to GCM", e);
      throw e;
    }
  }


  /**
   * Makes an HTTP POST request to a given endpoint.
   * <p/>
   * <p/>
   * <strong>Note: </strong> the returned connected should not be disconnected, otherwise it would
   * kill persistent connections made using Keep-Alive.
   *
   * @param url endpoint to post the request.
   * @param contentType type of request.
   * @param body body of the request.
   * @return the underlying connection.
   * @throws IOException propagated from underlying methods.
   */
  protected HttpURLConnection post(String url, String contentType, String body) throws IOException {
    if (url == null || body == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }
    byte[] bytes = body.getBytes();
    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setUseCaches(false);
    conn.setFixedLengthStreamingMode(bytes.length);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", contentType);
    conn.setRequestProperty("Authorization", "key=" + apiKey);
    OutputStream out = conn.getOutputStream();
    try {
      out.write(bytes);
    } finally {
      close(out);
    }
    return conn;
  }
}
