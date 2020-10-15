#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.jpa.json;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
* This BaseJSONConverter is based on example code by Zenida Blog
* https://zenidas.wordpress.com/recipes/jpa-converter-as-json/ JPA converter as JSON
* Goal : Create a generic JPA converted that stores and retrieves data as JSON
* Usage: Due to the fact that this class is abstract/generic, 
* we need to extend it to specific cases, like the following 
* (notice that you may use different extension classes for specific class types,
*  such as Image, Contact, etc., or simply use an Object base implementation, as shown below):
* 
* package mypackage.jpa.converter;
* 
* import javax.persistence.Converter;

* @Converter
* public class ObjectListConverter extends BaseListConverter<Object> {}* 
* 
* And using it is also a matter of associating the converter annotation to the appropriate persistent attribute, as follows:
* 
* package mypackage.model;
* 
* import mypackage.jpa.converter.ObjectListConverter;
* import javax.persistence.Column;
* import javax.persistence.Convert;
* import javax.persistence.Entity;
* 
* @Entity
* public class MyClass {
* 
* @Convert(converter = ObjectListConverter.class)
* @Column(name = "contacts")
* private List<Contact> contacts; // contact defines properties such as name and address
* 
* ... other stuff
* }
* 
*/
public abstract class BaseListConverter<T> extends BaseJSONConverter implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        final ObjectMapper mapper = getMapper();
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        final ObjectMapper mapper = getMapper();
        try {
            if (StringUtils.isBlank(dbData)) {
                return null;
            }
            return mapper.readValue(dbData, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            return null;
        }
    }
}
