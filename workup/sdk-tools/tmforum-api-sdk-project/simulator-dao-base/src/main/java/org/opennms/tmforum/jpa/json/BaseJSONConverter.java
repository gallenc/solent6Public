package org.opennms.tmforum.jpa.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * This BaseJSONConverter is based on example code by Zenida Blog
 * https://zenidas.wordpress.com/recipes/jpa-converter-as-json/ JPA converter as JSON
 * Goal : Create a generic JPA converted that stores and retrieves data as JSON
 */
public abstract class BaseJSONConverter {

  private static final ObjectMapper MAPPER;

  static {
    MAPPER = new ObjectMapper();
    // optional: customisations to the object mapper
    // MAPPER.registerModule(new JodaModule());
    MAPPER.registerModule(new JavaTimeModule());
    
    // prevents dao having problems with empty values
    MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    
    MAPPER.setSerializationInclusion(Include.NON_NULL);
    
    
    MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    
    
    
  }

  public static ObjectMapper getMapper() {
    return MAPPER;
  }
}