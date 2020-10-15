package org.opennms.tmforum.tmf650.api.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;

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
        genericNotification.setEventType(genericEvent.getClass().getSimpleName());
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
    public void testPostServiceProblemCreateEvent() {
        LOG.debug("start of test PostServiceProblemCreateEvent");

        String notification = serviceProblemCreateNotificationStr;

        LOG.debug("serviceProblemCreate=" + notification);

        Response response = target("/generic-listener/notification").request()
                .post(Entity.entity(notification, MediaType.APPLICATION_JSON));

        assertEquals("Should return status 200", 200, response.getStatus());

        String notificationReply = response.readEntity(String.class);
        LOG.debug("notificationReply=" + notificationReply);

        LOG.debug("end of test PostServiceProblemCreateEvent");
    }
    
    final String serviceProblemCreateNotificationStr = "{\n" + 
            "  \"eventId\": \"string\",\n" + 
            "  \"eventTime\": \"2020-08-08T07:29:47.050Z\",\n" + 
            "  \"eventType\": \"string\",\n" + 
            "  \"fieldPath\": \"string\",\n" + 
            "  \"resourcePath\": \"string\",\n" + 
            "  \"event\": {\n" + 
            "    \"serviceProblem\": {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"affectedNumberOfServices\": 0,\n" + 
            "      \"category\": \"string\",\n" + 
            "      \"correlationId\": \"string\",\n" + 
            "      \"description\": \"string\",\n" + 
            "      \"impactImportanceFactor\": \"string\",\n" + 
            "      \"originatingSystem\": \"string\",\n" + 
            "      \"priority\": 0,\n" + 
            "      \"problemEscalation\": \"string\",\n" + 
            "      \"reason\": \"string\",\n" + 
            "      \"resolutionDate\": \"2020-08-08T07:29:47.051Z\",\n" + 
            "      \"status\": \"string\",\n" + 
            "      \"statusChangeDate\": \"2020-08-08T07:29:47.051Z\",\n" + 
            "      \"statusChangeReason\": \"string\",\n" + 
            "      \"timeChanged\": \"2020-08-08T07:29:47.051Z\",\n" + 
            "      \"timeRaised\": \"2020-08-08T07:29:47.051Z\",\n" + 
            "      \"affectedLocation\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"role\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"affectedResource\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"affectedService\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"associatedSLAViolation\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"associatedTroubleTicket\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"correlationId\": \"string\",\n" + 
            "          \"status\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"comment\": [\n" + 
            "        {\n" + 
            "          \"author\": \"string\",\n" + 
            "          \"date\": \"2020-08-08T07:29:47.052Z\",\n" + 
            "          \"system\": \"string\",\n" + 
            "          \"text\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"extensionInfo\": [\n" + 
            "        {\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"valueType\": \"string\",\n" + 
            "          \"value\": {\n" + 
            "            \"anyStr\": \"string\"\n" + 
            "          },\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"firstAlert\": {\n" + 
            "        \"id\": \"string\",\n" + 
            "        \"href\": \"string\",\n" + 
            "        \"name\": \"string\",\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\",\n" + 
            "        \"@referredType\": \"string\"\n" + 
            "      },\n" + 
            "      \"impactPatterns\": {\n" + 
            "        \"description\": \"string\",\n" + 
            "        \"extensionInfo\": [\n" + 
            "          {\n" + 
            "            \"name\": \"string\",\n" + 
            "            \"valueType\": \"string\",\n" + 
            "            \"value\": {\n" + 
            "              \"anyStr\": \"string\"\n" + 
            "            },\n" + 
            "            \"@baseType\": \"string\",\n" + 
            "            \"@schemaLocation\": \"string\",\n" + 
            "            \"@type\": \"string\"\n" + 
            "          }\n" + 
            "        ],\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\"\n" + 
            "      },\n" + 
            "      \"originatorParty\": {\n" + 
            "        \"id\": \"string\",\n" + 
            "        \"href\": \"string\",\n" + 
            "        \"name\": \"string\",\n" + 
            "        \"role\": \"string\",\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\",\n" + 
            "        \"@referredType\": \"string\"\n" + 
            "      },\n" + 
            "      \"parentProblem\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"correlationId\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"relatedEvent\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"eventTime\": \"2020-08-08T07:29:47.053Z\",\n" + 
            "          \"eventType\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"relatedObject\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"relatedParty\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"role\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"responsibleParty\": {\n" + 
            "        \"id\": \"string\",\n" + 
            "        \"href\": \"string\",\n" + 
            "        \"name\": \"string\",\n" + 
            "        \"role\": \"string\",\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\",\n" + 
            "        \"@referredType\": \"string\"\n" + 
            "      },\n" + 
            "      \"rootCauseResource\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"name\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"rootCauseService\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"trackingRecord\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"description\": \"string\",\n" + 
            "          \"systemId\": \"string\",\n" + 
            "          \"time\": \"2020-08-08T07:29:47.054Z\",\n" + 
            "          \"user\": \"string\",\n" + 
            "          \"extensionInfo\": [\n" + 
            "            {\n" + 
            "              \"name\": \"string\",\n" + 
            "              \"valueType\": \"string\",\n" + 
            "              \"value\": {\n" + 
            "                \"anyStr\": \"string\"\n" + 
            "              },\n" + 
            "              \"@baseType\": \"string\",\n" + 
            "              \"@schemaLocation\": \"string\",\n" + 
            "              \"@type\": \"string\"\n" + 
            "            }\n" + 
            "          ],\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"underlyingAlarm\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"changeRequest\": {\n" + 
            "            \"id\": \"string\",\n" + 
            "            \"href\": \"string\",\n" + 
            "            \"@baseType\": \"string\",\n" + 
            "            \"@schemaLocation\": \"string\",\n" + 
            "            \"@type\": \"string\",\n" + 
            "            \"@referredType\": \"string\"\n" + 
            "          },\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"underlyingProblem\": [\n" + 
            "        {\n" + 
            "          \"id\": \"string\",\n" + 
            "          \"href\": \"string\",\n" + 
            "          \"correlationId\": \"string\",\n" + 
            "          \"@baseType\": \"string\",\n" + 
            "          \"@schemaLocation\": \"string\",\n" + 
            "          \"@type\": \"string\",\n" + 
            "          \"@referredType\": \"string\"\n" + 
            "        }\n" + 
            "      ],\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\"\n" + 
            "    }\n" + 
            "  }\n" + 
            "}";

}