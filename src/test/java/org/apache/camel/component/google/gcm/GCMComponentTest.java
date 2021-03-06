package org.apache.camel.component.google.gcm;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.gcm.producer.constants.CamelHeaderConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;

import java.util.HashMap;

public class GCMComponentTest extends AbstractGCMTestSupport {

    @Test
    public void testGoogleCloudMessaging() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        sendBody("direct:foo", "Hello World",
                new HashMap<String, Object>() {
                    {
                        put(CamelHeaderConstants.TO, "test");
                    }
                });

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:foo")
                        .to("google-cloud-messaging://bar?apiKey=test")
                        .to("mock:result");
            }
        };
    }
}
