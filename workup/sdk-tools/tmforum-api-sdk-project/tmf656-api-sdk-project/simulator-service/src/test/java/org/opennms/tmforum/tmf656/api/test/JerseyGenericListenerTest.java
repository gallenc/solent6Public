package org.opennms.tmforum.tmf656.api.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblem;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemAttributeValueChangeEvent;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemAttributeValueChangeNotification;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.model.GenericEvent;

import org.opennms.tmforum.tmf650.model.GenericNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.OffsetDateTime;

public class JerseyGenericListenerTest extends JerseyTest {
    private static Logger LOG = LoggerFactory.getLogger(JerseyGenericListenerTest.class);

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
    public void testPostGenericEvent() {
        LOG.debug("start of test PostGenericEvent");

        // ObjectReader or = NewJacksonFeature.getObjectReader();
        // ObjectWriter ow = NewJacksonFeature.getObjectWriter();
        ObjectMapper om = NewJacksonFeature.getObjectMapper();

        GenericEvent genericEvent = new GenericEvent();
        JsonNode jsonNode = om.valueToTree(genericEvent);

        GenericNotification genericNotification = new GenericNotification();
        genericNotification.setEvent(jsonNode);

        genericNotification.setEventId("10");
        OffsetDateTime eventTime = OffsetDateTime.now();
        genericNotification.setEventTime(eventTime);
        genericNotification.setEventType("GenericNotification");
        // genericNotification.setFieldPath(fieldPath);
        // genericNotification.setResourcePath(resourcePath);

        LOG.debug("genericNotification=" + genericNotification);

        Response response = target("/generic-listener/notification").request()
                .post(Entity.entity(genericNotification, MediaType.APPLICATION_JSON));

        assertEquals("Should return status 200", 200, response.getStatus());

        String notificationReply = response.readEntity(String.class);
        LOG.debug("notificationReply=" + notificationReply);

        LOG.debug("end of test PostGenericEvent");
    }

    @Test
    public void testPostServiceProblemAttributeValueChangeEvent() {
        LOG.debug("start of test PostServiceProblemAttributeValueChangeEvent");

        ServiceProblem serviceProblem = new ServiceProblem();
        ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
        event.setServiceProblem(serviceProblem);
        ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
        notification.setEventType("ServiceProblemAttributeValueChangeNotification");
        notification.setEvent(event);
        notification.setEventId("10");
        OffsetDateTime eventTime = OffsetDateTime.now();
        notification.setEventTime(eventTime);

        // genericNotification.setFieldPath(fieldPath);
        // genericNotification.setResourcePath(resourcePath);

        LOG.debug("serviceProblemAttributeValueChangeNotification=" + notification);

        Response response = target("/generic-listener/notification").request()
                .post(Entity.entity(notification, MediaType.APPLICATION_JSON));

        assertEquals("Should return status 200", 200, response.getStatus());

        String notificationReply = response.readEntity(String.class);
        LOG.debug("notificationReply=" + notificationReply);

        LOG.debug("end of test PostServiceProblemAttributeValueChangeEvent");
    }

}