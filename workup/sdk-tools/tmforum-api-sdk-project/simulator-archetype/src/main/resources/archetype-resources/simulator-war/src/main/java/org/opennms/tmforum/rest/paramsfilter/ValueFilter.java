#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;

public abstract class ValueFilter extends Filter{
    
    ValueFilter(CriteriaBuilder cb){
        super(cb);
    }
    
    public abstract void setValue(String value);

    @Override
    public abstract String getQuery() ;

}
