#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.service.test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblem;
import org.opennms.tmforum.${tmfSpecPackageName}.service.FieldFilter;
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
    
    
    
    
    
    
    
    
    
    
    
    public static final String EXAMPLE_SERVICE_PROBLEM ="{${symbol_escape}n" + 
            "  ${symbol_escape}"affectedNumberOfServices${symbol_escape}": 0,${symbol_escape}n" + 
            "  ${symbol_escape}"category${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"correlationId${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"description${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"impactImportanceFactor${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"originatingSystem${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"priority${symbol_escape}": 0,${symbol_escape}n" + 
            "  ${symbol_escape}"problemEscalation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"reason${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"resolutionDate${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.020Z${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"status${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"statusChangeDate${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.020Z${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"statusChangeReason${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"timeChanged${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.021Z${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"timeRaised${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.021Z${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"affectedLocation${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"role${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"affectedResource${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"affectedService${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"associatedSLAViolation${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"associatedTroubleTicket${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"correlationId${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"status${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"comment${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"author${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"date${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.022Z${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"system${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"text${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"extensionInfo${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"valueType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"value${symbol_escape}": {${symbol_escape}n" + 
            "        ${symbol_escape}"anyStr${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "      },${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"firstAlert${symbol_escape}": {${symbol_escape}n" + 
            "    ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "  },${symbol_escape}n" + 
            "  ${symbol_escape}"impactPatterns${symbol_escape}": {${symbol_escape}n" + 
            "    ${symbol_escape}"description${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"extensionInfo${symbol_escape}": [${symbol_escape}n" + 
            "      {${symbol_escape}n" + 
            "        ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"valueType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"value${symbol_escape}": {${symbol_escape}n" + 
            "          ${symbol_escape}"anyStr${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "        },${symbol_escape}n" + 
            "        ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "      }${symbol_escape}n" + 
            "    ],${symbol_escape}n" + 
            "    ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "  },${symbol_escape}n" + 
            "  ${symbol_escape}"originatorParty${symbol_escape}": {${symbol_escape}n" + 
            "    ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"role${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "  },${symbol_escape}n" + 
            "  ${symbol_escape}"parentProblem${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"correlationId${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"relatedEvent${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"eventTime${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.022Z${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"eventType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"relatedObject${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"relatedParty${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"role${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"responsibleParty${symbol_escape}": {${symbol_escape}n" + 
            "    ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"role${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "    ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "  },${symbol_escape}n" + 
            "  ${symbol_escape}"rootCauseResource${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"rootCauseService${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"trackingRecord${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"description${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"systemId${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"time${symbol_escape}": ${symbol_escape}"2020-05-06T10:39:12.022Z${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"user${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"extensionInfo${symbol_escape}": [${symbol_escape}n" + 
            "        {${symbol_escape}n" + 
            "          ${symbol_escape}"name${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "          ${symbol_escape}"valueType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "          ${symbol_escape}"value${symbol_escape}": {${symbol_escape}n" + 
            "            ${symbol_escape}"anyStr${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "          },${symbol_escape}n" + 
            "          ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "          ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "          ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "        }${symbol_escape}n" + 
            "      ],${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"underlyingAlarm${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"changeRequest${symbol_escape}": {${symbol_escape}n" + 
            "        ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "        ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "      },${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"underlyingProblem${symbol_escape}": [${symbol_escape}n" + 
            "    {${symbol_escape}n" + 
            "      ${symbol_escape}"id${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"href${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"correlationId${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "      ${symbol_escape}"@referredType${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "    }${symbol_escape}n" + 
            "  ],${symbol_escape}n" + 
            "  ${symbol_escape}"@baseType${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"@schemaLocation${symbol_escape}": ${symbol_escape}"string${symbol_escape}",${symbol_escape}n" + 
            "  ${symbol_escape}"@type${symbol_escape}": ${symbol_escape}"string${symbol_escape}"${symbol_escape}n" + 
            "}${symbol_escape}n" + 
            "${symbol_escape}n" + 
            "Parameter content type";
    

}
