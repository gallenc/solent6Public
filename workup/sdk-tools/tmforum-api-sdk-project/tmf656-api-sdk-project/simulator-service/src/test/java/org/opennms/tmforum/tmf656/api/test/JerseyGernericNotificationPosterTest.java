package org.opennms.tmforum.tmf656.api.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Ignore;
import org.junit.Test;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblem;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemAttributeValueChangeEvent;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemAttributeValueChangeNotification;
import org.opennms.tmforum.tmf650.hub.impl.GenericRestNotificationPoster;
import org.opennms.tmforum.tmf650.hub.impl.NotificationPoster;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;

public class JerseyGernericNotificationPosterTest extends JerseyTest {
    private static Logger LOG = LoggerFactory.getLogger(JerseyGernericNotificationPosterTest.class);

    @Override
    protected Application configure() {
        LOG.debug("starting jetty for test");
        ResourceConfig rc = new ResourceConfig();

        rc.register(SpringLifecycleListener.class);
        // rc.register(RequestContextFilter.class);
        rc.packages("org.opennms.tmforum.tmf650.api", "org.opennms.tmforum.tmf650.api.model",
                "org.opennms.tmforum.tmf650.api.rest", "org.opennms.tmforum.tmf650.api.impl", "org.opennms.tmforum.tmf650.listener.impl", "org.opennms.tmforum.tmf650.hub.impl");

        rc.property("contextConfigLocation", "classpath:spring-simple-rest-test-context.xml");

        // configures jackson data binding
        rc.register(NewJacksonFeature.class);

        return rc;
    }

    @Override
    public void configureClient(ClientConfig config) {
        // configures jackson data binding
        config.register(NewJacksonFeature.class);
    }

    @Test
    public void testsuccessmessage() throws URISyntaxException {
        LOG.debug("starting testsuccessmessage()");
        int timeout=5000;
        long maxConsecutiveErrors=3;
        
        String notificationFilter=null;
        URI uri= new URI("http://localhost:9998/generic-listener/notification");
        
        NotificationPoster restNotificationPoster = new GenericRestNotificationPoster(uri , timeout, maxConsecutiveErrors, notificationFilter, UUID.randomUUID().toString(), null);
        
        // post ServiceProblemAttributeValueChangeNotification
        ServiceProblem serviceProblem = new ServiceProblem();
        ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
        event.setServiceProblem(serviceProblem);
        ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
        notification.setEvent(event);
        notification.setEventId("10");
        OffsetDateTime eventTime = OffsetDateTime.now();
        notification.setEventTime(eventTime);

        notification.setEventType("ServiceProblemAttributeValueChangeNotification");

        restNotificationPoster.postNotification(notification);
        
        LOG.debug("waiting for reply after post");
        try {
            Thread.sleep(10000); // wait for reply
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOG.debug("end of testsuccessmessage()");
    }
    
}
   