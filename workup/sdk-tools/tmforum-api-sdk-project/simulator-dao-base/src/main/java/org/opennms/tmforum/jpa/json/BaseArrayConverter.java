package org.opennms.tmforum.jpa.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

/*
 * This Json Array converter is based on example code by Zenida Blog
 * https://zenidas.wordpress.com/recipes/jpa-converter-as-json/ JPA converter as JSON
 * Goal : Create a generic JPA converted that stores and retrieves data as JSON
 * 
 * 
 * usage 1. extend to concrete class
 * 
 * import javax.persistence.Converter;
 * 
 * @Converter public class ObjectArrayConverter extends BaseArrayConverter<Object> {}
 * 
 * 2. use class in annotations on Entity object array fields
 * 
 * package mypackage.model;
 * 
 * import mypackage.jpa.converter.ObjectArrayConverter; import
 * javax.persistence.Column; import javax.persistence.Convert; import
 * javax.persistence.Entity;
 * 
 * @Entity public class MyClass {
 * 
 * @Converter(converter = ObjectArrayConverter.class)
 * 
 * @Column(name = "contacts") private Contact[] contacts; 
 * // contact defines properties such as name and address
 * 
 * ... other stuff }
 * 
 */
public abstract class BaseArrayConverter<T> extends BaseJSONConverter implements AttributeConverter<T[], String> {

    private Class<T[]> entityTypeClass;

    public String convertToDatabaseColumn(T[] attribute) {
        final ObjectMapper mapper = getMapper();
        if (attribute == null || attribute.length == 0) {
            return "";
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public T[] convertToEntityAttribute(String dbData) {
        final ObjectMapper mapper = getMapper();
        try {
            if (StringUtils.isBlank(dbData)) {
                return null;
            }
            return mapper.readValue(dbData, getEntityTypeClass());
        } catch (IOException e) {
            return null;
        }
    }

    private void initGenericType() {
        if (entityTypeClass == null) {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            entityTypeClass = ((Class<T[]>) parameterizedType.getActualTypeArguments()[0]);
        }
    }

    private Class<T[]> getEntityTypeClass() {
        initGenericType();
        return entityTypeClass;
    }
}


