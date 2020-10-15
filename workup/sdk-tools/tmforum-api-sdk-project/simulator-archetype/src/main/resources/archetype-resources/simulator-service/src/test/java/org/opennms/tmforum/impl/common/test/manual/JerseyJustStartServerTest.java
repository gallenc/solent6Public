#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.impl.common.test.manual;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.RequestContextFilter;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;  

public class JerseyJustStartServerTest extends JerseyTest {
    private static Logger LOG = LoggerFactory.getLogger(JerseyJustStartServerTest.class);

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
    public void testJustStartServer() {
        LOG.debug("starting generic hub test  - waiting");

        try {
            Thread.sleep(120000); // 2 minute
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LOG.debug("end of generic hub test");
    }

}