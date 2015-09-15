package org.apache.camel.component.google.gcm.producer;

import org.apache.camel.component.google.gcm.model.GCMBody;
import org.apache.camel.component.google.gcm.model.GCMResponse;
import org.apache.camel.component.google.gcm.model.MultiCastResponse;

import java.io.IOException;
import java.util.Set;

/**
 * Created by miki on 16.04.2015.
 */
public interface IGCMProducer {

  public MultiCastResponse send(GCMBody message, Set<String> regIds) throws IOException;

  public MultiCastResponse send(GCMBody message, String regId) throws IOException;

}
