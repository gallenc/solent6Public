#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter.old;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

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


    @Override
    public Expression getExpression() {
        // TODO Auto-generated method stub
        return null;
    }


}
