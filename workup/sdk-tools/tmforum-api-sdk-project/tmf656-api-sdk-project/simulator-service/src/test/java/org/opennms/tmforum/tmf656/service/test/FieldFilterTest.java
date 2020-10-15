package org.opennms.tmforum.tmf656.service.test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;
import org.opennms.tmforum.simulator.base.service.FieldFilter;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FieldFilterTest {
    private static Logger LOG = LoggerFactory.getLogger(FieldFilterTest.class);
    
    private ObjectMapper MAPPER;
    private ServiceProblem initialServiceProblem;
    
    @Before
    public void init() throws IOException {
        
        MAPPER = new ObjectMapper();
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.registerModule(new JavaTimeModule());
        initialServiceProblem = MAPPER.readValue(EXAMPLE_SERVICE_PROBLEM, ServiceProblem.class);

    }
    
    @Test
    public void testFilterWorks() throws IOException {
        String DESIRED_FIELDS = "affectedNumberOfServices,category";
        
        LOG.debug("testing filter: "+DESIRED_FIELDS);

        FieldFilter<ServiceProblem> fieldFilter = new FieldFilter<ServiceProblem>();

        initialServiceProblem = fieldFilter.filter(initialServiceProblem, DESIRED_FIELDS, null);
        
        Writer writer = new StringWriter();
        MAPPER.writeValue(writer, initialServiceProblem);
        LOG.debug("filtered serviceProblem: "+writer.toString());


    }
    
    
    
    
    
    
    
    
    
    
    
    public static final String EXAMPLE_SERVICE_PROBLEM ="{\n" + 
            "  \"affectedNumberOfServices\": 0,\n" + 
            "  \"category\": \"string\",\n" + 
            "  \"correlationId\": \"string\",\n" + 
            "  \"description\": \"string\",\n" + 
            "  \"impactImportanceFactor\": \"string\",\n" + 
            "  \"originatingSystem\": \"string\",\n" + 
            "  \"priority\": 0,\n" + 
            "  \"problemEscalation\": \"string\",\n" + 
            "  \"reason\": \"string\",\n" + 
            "  \"resolutionDate\": \"2020-05-06T10:39:12.020Z\",\n" + 
            "  \"status\": \"string\",\n" + 
            "  \"statusChangeDate\": \"2020-05-06T10:39:12.020Z\",\n" + 
            "  \"statusChangeReason\": \"string\",\n" + 
            "  \"timeChanged\": \"2020-05-06T10:39:12.021Z\",\n" + 
            "  \"timeRaised\": \"2020-05-06T10:39:12.021Z\",\n" + 
            "  \"affectedLocation\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"role\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"affectedResource\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"affectedService\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"associatedSLAViolation\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"associatedTroubleTicket\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"correlationId\": \"string\",\n" + 
            "      \"status\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"comment\": [\n" + 
            "    {\n" + 
            "      \"author\": \"string\",\n" + 
            "      \"date\": \"2020-05-06T10:39:12.022Z\",\n" + 
            "      \"system\": \"string\",\n" + 
            "      \"text\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"extensionInfo\": [\n" + 
            "    {\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"valueType\": \"string\",\n" + 
            "      \"value\": {\n" + 
            "        \"anyStr\": \"string\"\n" + 
            "      },\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"firstAlert\": {\n" + 
            "    \"id\": \"string\",\n" + 
            "    \"href\": \"string\",\n" + 
            "    \"name\": \"string\",\n" + 
            "    \"@baseType\": \"string\",\n" + 
            "    \"@schemaLocation\": \"string\",\n" + 
            "    \"@type\": \"string\",\n" + 
            "    \"@referredType\": \"string\"\n" + 
            "  },\n" + 
            "  \"impactPatterns\": {\n" + 
            "    \"description\": \"string\",\n" + 
            "    \"extensionInfo\": [\n" + 
            "      {\n" + 
            "        \"name\": \"string\",\n" + 
            "        \"valueType\": \"string\",\n" + 
            "        \"value\": {\n" + 
            "          \"anyStr\": \"string\"\n" + 
            "        },\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\"\n" + 
            "      }\n" + 
            "    ],\n" + 
            "    \"@baseType\": \"string\",\n" + 
            "    \"@schemaLocation\": \"string\",\n" + 
            "    \"@type\": \"string\"\n" + 
            "  },\n" + 
            "  \"originatorParty\": {\n" + 
            "    \"id\": \"string\",\n" + 
            "    \"href\": \"string\",\n" + 
            "    \"name\": \"string\",\n" + 
            "    \"role\": \"string\",\n" + 
            "    \"@baseType\": \"string\",\n" + 
            "    \"@schemaLocation\": \"string\",\n" + 
            "    \"@type\": \"string\",\n" + 
            "    \"@referredType\": \"string\"\n" + 
            "  },\n" + 
            "  \"parentProblem\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"correlationId\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"relatedEvent\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"eventTime\": \"2020-05-06T10:39:12.022Z\",\n" + 
            "      \"eventType\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"relatedObject\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"relatedParty\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"role\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"responsibleParty\": {\n" + 
            "    \"id\": \"string\",\n" + 
            "    \"href\": \"string\",\n" + 
            "    \"name\": \"string\",\n" + 
            "    \"role\": \"string\",\n" + 
            "    \"@baseType\": \"string\",\n" + 
            "    \"@schemaLocation\": \"string\",\n" + 
            "    \"@type\": \"string\",\n" + 
            "    \"@referredType\": \"string\"\n" + 
            "  },\n" + 
            "  \"rootCauseResource\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"name\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"rootCauseService\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"trackingRecord\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"description\": \"string\",\n" + 
            "      \"systemId\": \"string\",\n" + 
            "      \"time\": \"2020-05-06T10:39:12.022Z\",\n" + 
            "      \"user\": \"string\",\n" + 
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
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"underlyingAlarm\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"changeRequest\": {\n" + 
            "        \"id\": \"string\",\n" + 
            "        \"href\": \"string\",\n" + 
            "        \"@baseType\": \"string\",\n" + 
            "        \"@schemaLocation\": \"string\",\n" + 
            "        \"@type\": \"string\",\n" + 
            "        \"@referredType\": \"string\"\n" + 
            "      },\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"underlyingProblem\": [\n" + 
            "    {\n" + 
            "      \"id\": \"string\",\n" + 
            "      \"href\": \"string\",\n" + 
            "      \"correlationId\": \"string\",\n" + 
            "      \"@baseType\": \"string\",\n" + 
            "      \"@schemaLocation\": \"string\",\n" + 
            "      \"@type\": \"string\",\n" + 
            "      \"@referredType\": \"string\"\n" + 
            "    }\n" + 
            "  ],\n" + 
            "  \"@baseType\": \"string\",\n" + 
            "  \"@schemaLocation\": \"string\",\n" + 
            "  \"@type\": \"string\"\n" + 
            "}\n" + 
            "\n" + 
            "Parameter content type";
    

}
