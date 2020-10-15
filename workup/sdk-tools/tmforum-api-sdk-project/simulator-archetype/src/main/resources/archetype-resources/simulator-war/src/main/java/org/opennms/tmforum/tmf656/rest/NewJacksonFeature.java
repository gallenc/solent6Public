#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.rest;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class NewJacksonFeature implements Feature {
    private static Logger LOG = LoggerFactory.getLogger(NewJacksonFeature.class);

    private static final ObjectMapper MAPPER;

    static {

        // Create the new object mapper.
        MAPPER = new ObjectMapper();

        // Enable/disable various configuration flags.
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       // MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.registerModule(new JavaTimeModule());

    }
    @Override
    public boolean configure(final FeatureContext context) {
        LOG.debug("NewJacksonFeature configure called");
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider(
                MAPPER, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
        context.register(provider);

        return true;
    }
}