package org.opennms.tmforum.tmf650.api.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Ignore;
import org.junit.Test;
import org.opennms.tmforum.tmf650.hub.impl.GenericRestNotificationPoster;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcherImpl;
import org.opennms.tmforum.tmf650.hub.impl.NotificationPoster;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.model.GenericEvent;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericNotification;

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
    public void testsubscribe() throws URISyntaxException, JsonMappingException, JsonProcessingException {
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
//            ServiceProblem serviceProblem = new ServiceProblem();
//            ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
//            event.setServiceProblem(serviceProblem);
//            ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
            
            ObjectMapper om = NewJacksonFeature.getObjectMapper();

            GenericEvent genericEvent = new GenericEvent();
            JsonNode jsonNode = om.valueToTree(genericEvent);
            
            JsonNode parsedEvent =  om.readTree(serviceProblemCreateEventStr);
            GenericNotification genericNotification = new GenericNotification();
            genericNotification.setEvent(jsonNode);
            
            GenericNotification notification = new GenericNotification();

            notification.setEvent(parsedEvent);
            
            notification.setEventId("10");
            OffsetDateTime eventTime = OffsetDateTime.now();
            notification.setEventTime(eventTime);

            notification.setEventType("serviceProblemCreate");

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
    
    final String serviceProblemCreateEventStr = "{\n" + 
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
            "  }\n";


}
