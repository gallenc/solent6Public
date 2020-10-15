#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FieldFilter<T> {

    /**
     * filters object, making fields null unless named in userFields or
     * defaultFields
     * 
     * If fields contains 'none', then only href and id are returned
     * 
     * @param objectToFilter objectToFilter object which will have fields made null.
     *                       (Note this object is modified not cloned).
     * @param fields         comma separated list of field names to return. All
     *                       other fields will be null unless included in
     *                       defaultFields. If null, no filter will be applied
     * @param defaultFields  fields which will always be returned (as list of
     *                       string)
     * @return
     */
    public T filter(T objectToFilter, String fields, List<String> defaultFields) {

        if (fields == null || fields.isEmpty()) {
            return objectToFilter;
        }

        // removes leading and trailing spaces for all values and checks there are no
        // empty values in fields string
        List<String> userFields = new ArrayList<String>();
        String[] uf = fields.split(",");
        for (String f : uf) {
            String trimF = f.trim();
            if (trimF.isEmpty()) {
                throw new IllegalArgumentException("cannot have empty fields in fields string:" + fields);
            }
            userFields.add(trimF);
        }

        return filter(objectToFilter, userFields, defaultFields);

    }

    /**
     * filters object, making fields null unless named in userFields or
     * defaultFields
     * 
     * If userFields contains 'none', then only href and id are returned
     * 
     * @param objectToFilter object which will have fields made null
     * @param userFields     fields which user wants returned
     * @param defaultFields  fields which will always be returned (as list of
     *                       string). if null then only fields in userFields
     *                       returned.
     * @return
     */
    public T filter(T objectToFilter, List<String> userFields, List<String> defaultFields) {

        if (userFields.isEmpty()) {
            return objectToFilter;
        }

        List<String> allfields = new ArrayList<String>();

        if (userFields.contains("none")) {
            allfields.addAll(Arrays.asList("href", "id"));
        } else {
            allfields.addAll(userFields);
            if (defaultFields != null) {
                allfields.addAll(defaultFields);
            }
        }

        // get objects fields and check if all requested fields can be returned by this
        // object
        Field[] f = objectToFilter.getClass().getDeclaredFields();
        HashMap<String, Field> fieldMap = new HashMap<String, Field>();
        for (Field field : f) {
            fieldMap.put(field.getName(), field);
        }
        boolean error = false;
        StringBuffer message = new StringBuffer();
        for (String fieldName : allfields) {
            if (!fieldMap.containsKey(fieldName)) {
                error = true;
                message.append(fieldName).append(",");
            }
        }
        if (error) {
            throw new IllegalArgumentException(
                    "cannot filter " + objectToFilter.getClass().getName() + " unknown fields:" + message.toString());
        }

        // make all values null unless included in fields
        for (String fieldName : fieldMap.keySet()) {
            if (!allfields.contains(fieldName)) {
                Field property = fieldMap.get(fieldName);
                property.setAccessible(true);
                try {
                    property.set(objectToFilter, null);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("problem filtering " + objectToFilter.getClass().getName(), ex);
                }
            }
        }

        return objectToFilter;

    }
}
