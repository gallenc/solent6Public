#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.api.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Ignore;
import org.junit.Test;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblem;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemAttributeValueChangeEvent;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemAttributeValueChangeNotification;
import org.opennms.tmforum.tmf650.hub.impl.GenericRestNotificationPoster;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcherImpl;
import org.opennms.tmforum.tmf650.hub.impl.NotificationPoster;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;

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

public class JerseyNotificationDispatcherTest extends JerseyTest {
    private static Logger LOG = LoggerFactory.getLogger(JerseyNotificationDispatcherTest.class);

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
    public void testsubscribe() throws URISyntaxException {
        LOG.debug("starting testsubscribe()");
        int timeout = 5000;
        long maxConsecutiveErrors = 3;
        String notificationFilter = null;

        NotificationDispatcher notificationDispatcher = new NotificationDispatcherImpl();
        notificationDispatcher.setMaxConsecutiveErrors(maxConsecutiveErrors);
        notificationDispatcher.setTimeout(timeout);

        // working subscription
        GenericEventSubscriptionInput subscription = new GenericEventSubscriptionInput();
        subscription.callback("http://localhost:9998/generic-listener/notification");
        subscription.setQuery("");
        notificationDispatcher.registerListener(subscription);

        // not working subscription
        GenericEventSubscriptionInput subscription2 = new GenericEventSubscriptionInput();
        subscription2.callback("http://localhost:9999/generic-listener/notification");
        subscription2.setQuery("");
        notificationDispatcher.registerListener(subscription2);

        LOG.debug("statistics after subscriptions:" + notificationDispatcher.getAllSubscriptionStatistics());

        // post 4 ServiceProblemAttributeValueChangeNotification
        for (int i = 1; i < 5; i++) {
            ServiceProblem serviceProblem = new ServiceProblem();
            ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
            event.setServiceProblem(serviceProblem);
            ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
            notification.setEvent(event);
            notification.setEventId(Integer.toString(i));
            OffsetDateTime eventTime = OffsetDateTime.now();
            notification.setEventTime(eventTime);

            notification.setEventType(event.getClass().getSimpleName());

            notificationDispatcher.sendNotification(notification);
        }

        LOG.debug("waiting for reply after post");
        try {
            Thread.sleep(10000); // wait for reply
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOG.debug("statistics after notifications:" + notificationDispatcher.getAllSubscriptionStatistics());

        LOG.debug("end of testsubscribe()");
    }

}
