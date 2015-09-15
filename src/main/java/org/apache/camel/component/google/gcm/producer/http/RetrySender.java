package org.apache.camel.component.google.gcm.producer.http;

import org.apache.camel.component.google.gcm.model.GCMBody;
import org.apache.camel.component.google.gcm.model.GCMResponse;
import org.apache.camel.component.google.gcm.model.MultiCastResponse;
import org.apache.camel.component.google.gcm.producer.GCMProducer;
import org.apache.camel.component.google.gcm.producer.IGCMProducer;

import java.io.IOException;
import java.util.Set;

/**
 * Not really a producer, just implementing the interface. decorating the real producer;
 * Also, not singleton. will be instantiated every time but with the singleton decorator param;
 * Retry functionality decorator over SimpleSender.;
 * Created by miki on 15.04.2015.
 * TODO: implement this;
 */
public class RetrySender implements IGCMProducer {

    private final GCMProducer simpleSender;
    private final int retries;

    public RetrySender(GCMProducer decoratedSender, int retries) {
        this.simpleSender = decoratedSender;
        this.retries = retries;
    }

    @Override
    public MultiCastResponse send(GCMBody message, Set<String> regIds) throws IOException {

        return simpleSender.send(message, regIds);
    }

    @Override
    public MultiCastResponse send(GCMBody message, String regId)throws IOException {
        return simpleSender.send(message, regId);
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
