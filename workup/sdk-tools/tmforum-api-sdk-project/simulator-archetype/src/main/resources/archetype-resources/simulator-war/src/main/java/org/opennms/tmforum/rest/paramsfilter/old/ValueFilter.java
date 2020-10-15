#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter.old;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public abstract class ValueFilter extends Filter{
    
    ValueFilter(CriteriaBuilder cb){
        super(cb);
    }
    
    public abstract void setValue(String value);

    @Override
    public abstract String getQuery() ;
    
    @Override
    public abstract Expression  getExpression() ;

}
