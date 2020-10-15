#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.api.test;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.listener.impl.GenericNotificationListener;
import org.opennms.tmforum.tmf650.listener.impl.GenericNotificationSubscriber;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.RequestContextFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JerseyGenericHubTest extends JerseyTest implements GenericNotificationListener {
    private static Logger LOG = LoggerFactory.getLogger(JerseyGenericHubTest.class);

    // code to get access to spring beans
    // see
    // https://stackoverflow.com/questions/18282409/retrieve-a-managed-bean-from-a-jerseytest-container-with-jersey-spring3
    // for an alternative see https://github.com/rj-hwang/jersey-spring-test
    
    private List<GenericNotification> receivedNotifications= new CopyOnWriteArrayList();

    @Inject
    NotificationDispatcher notificationDispatcher;
    
    @Inject
    GenericNotificationSubscriber genericNotificationSubscriber;

    @Override
    protected Application configure() {
        LOG.debug("starting jetty for test");
        ResourceConfig rc = new ResourceConfig();

        rc.register(SpringLifecycleListener.class);
        // rc.register(RequestContextFilter.class);
        rc.packages("org.opennms.tmforum.tmf650.api", "org.opennms.tmforum.tmf650.api.model",
                "org.opennms.tmforum.tmf650.api.rest", "org.opennms.tmforum.tmf650.api.impl", 
                "org.opennms.tmforum.tmf650.listener.impl", "org.opennms.tmforum.tmf650.hub.impl");

        rc.property("contextConfigLocation", "classpath:spring-simple-rest-test-context.xml");

        // configures jackson data binding
        rc.register(NewJacksonFeature.class);

        // to allow spring autowired to work on the test class
        // this creates jersey warnings but is OK
        rc.register(this);

        return rc;
    }

    @Override
    public void configureClient(ClientConfig config) {
        // configures jackson data binding
        config.register(NewJacksonFeature.class);
    }

    @Before
    public void setupListener() {
        assertNotNull(notificationDispatcher);
        assertNotNull(genericNotificationSubscriber);
        
        receivedNotifications.clear();
        genericNotificationSubscriber.subscribe(this);

    }
    
    @After 
    public void tearDownListener() {
        
        genericNotificationSubscriber.unsubscribe(this);
        
    }

    @Test
    public void testget() {
        LOG.debug("start of test get");
        GenericEventSubscriptionInput subscriptionRequest = new GenericEventSubscriptionInput();
        LOG.debug("subscriptionRequest=" + subscriptionRequest);

        Response response = target("/generic-hub").request().get();
        assertEquals("Should return status 200", 200, response.getStatus());

        String subscriptionReply = response.readEntity(String.class);
        LOG.debug("subscriptionReply=" + subscriptionReply);

        LOG.debug("end of test get");
    }

    @Test
    public void testAddAndDeleteSubscription() {
        LOG.debug("start of test add and delete subscription");
        GenericEventSubscriptionInput subscriptionRequest = new GenericEventSubscriptionInput();
        LOG.debug("subscriptionRequest=" + subscriptionRequest);

        String callback = "http://localhost:9998/generic-listener/notification";
        subscriptionRequest.setCallback(callback);
        String query = null;
        subscriptionRequest.setQuery(query);

        Response response = target("/generic-hub").request()
                .post(Entity.entity(subscriptionRequest, MediaType.APPLICATION_JSON));
        assertEquals("Should return status 200", 200, response.getStatus());

        GenericEventSubscription subscriptionReply = response.readEntity(GenericEventSubscription.class);
        LOG.debug("subscriptionReply=" + subscriptionReply);

        String id = subscriptionReply.getId();

        LOG.debug("end of test add subscription");
        
        LOG.debug("test send events to subscription");
        for(int i = 1; i<4 ;i++) {
            GenericNotification notification = new GenericNotification() ;
            notification.setEventId(Integer.toString(i));
            notificationDispatcher.sendNotification(notification);
        }
        
        LOG.debug("end send events to subscription");
        

        LOG.debug("start of test delete subscription id=" + id);

        response = target("/generic-hub").path(id).request(MediaType.APPLICATION_JSON).delete();
        assertEquals("Should return status 204", 204, response.getStatus());

        LOG.debug("end of test delete subscription");

    }

    @Override
    public void onNotification(GenericNotification genericNotification) {
        LOG.debug("received notification: "+genericNotification);
        receivedNotifications.add(genericNotification);
        
    }

}