#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.jpa.json;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* This Object converter is based on example code by Zenida Blog
 * https://zenidas.wordpress.com/recipes/jpa-converter-as-json/ JPA converter as JSON
 * Goal : Create a generic JPA converted that stores and retrieves data as JSON
 * Notice that this implementation stores also the name of the class as part of the generated string so that it may be used in different scenarios, when fetching the result from the database, i.e., to be able to store and retrieve different classes being stored with the same converter.
 * 
 * Usage:  just a matter of associating the converter to the desired embeddable attribute, as follows:
 * 
 * package mypackage.model;
 * 
 * import mypackage.jpa.converter.ObjectConverter;
 * import javax.persistence.Column;
 * import javax.persistence.Convert;
 * import javax.persistence.Entity;
 * 
 * @Entity
 * public class MyClass {
 * 
 *   @Convert(converter = ObjectConverter.class)
 *   @Column(name = "contact")
 *   private Contact contact; // contact defines properties such as name and address
 * 
 *   ... other stuff
 * }
 *
 */
@Converter
public class ObjectConverter extends BaseJSONConverter implements AttributeConverter<Object, String> {

  private final static Logger LOGGER = LoggerFactory.getLogger(ObjectConverter.class);

  @Override
  public String convertToDatabaseColumn(Object attribute) {
    final ObjectMapper mapper = getMapper();
    if (attribute == null) {
      return "";
    }
    try {
      return attribute.getClass().getName() + "|" + mapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      LOGGER.error("Exception while converting to database column", e);
      return null;
    }
  }

  @Override
  public Object convertToEntityAttribute(String dbData) {
    final ObjectMapper mapper = getMapper();
    try {
      if (StringUtils.isBlank(dbData)) {
        return null;
      }
      final String[] parts = dbData.split("${symbol_escape}${symbol_escape}|", 2);
      return mapper.readValue(parts[1], Class.forName(parts[0]));
    } catch (Exception e) {
      LOGGER.error("Exception while converting to entity attribute", e);
      return null;
    }
  }
}