package org.opennms.tmforum.rest.paramsfilter;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;

public class ValueFilterSimpleImpl extends ValueFilter {
    
    ValueFilterSimpleImpl(CriteriaBuilder cb) {
        super(cb);
    }


    String value=null;

    @Override
    public void setValue(String value) {
        Objects.requireNonNull(value);
        this.value=value;
    }


    @Override
    public String getQuery() {
        return " '"+value+"' ";
    }


}
