package org.opennms.tmforum.tmf656.rest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// see https://stackoverflow.com/questions/18872931/custom-objectmapper-with-jersey-2-2-and-jackson-2-1
// added to allow JavaTimeModule()
@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private static Logger LOG = LoggerFactory.getLogger(JacksonObjectMapperProvider.class);
    
    final ObjectMapper defaultObjectMapper;

    public JacksonObjectMapperProvider() {
        defaultObjectMapper = createDefaultMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        LOG.debug("getContext called");
        return defaultObjectMapper;
    }

    public static ObjectMapper createDefaultMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        // any changes to the ObjectMapper is up to you. Do what you like.

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        

        return objectMapper;
    }
}