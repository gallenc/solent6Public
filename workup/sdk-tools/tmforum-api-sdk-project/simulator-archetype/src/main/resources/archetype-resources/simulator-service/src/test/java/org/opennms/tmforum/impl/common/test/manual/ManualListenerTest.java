#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.impl.common.test.manual;

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

/** this test sets up a generic listener and waits for notifications 
 *  at http://localhost:9998/generic-listener/notification
*/
public class ManualListenerTest extends JerseyTest implements GenericNotificationListener {
    private static Logger LOG = LoggerFactory.getLogger(ManualListenerTest.class);

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
    public void testJustListenerServer() {
        LOG.debug("starting generic listener test  - waiting");

        try {
            Thread.sleep(120000); // 2 minute
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOG.debug("end of generic listener test");
    }

    @Override
    public void onNotification(GenericNotification genericNotification) {
        LOG.debug("received notification: "+genericNotification);
        receivedNotifications.add(genericNotification);
        
    }

}