#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import javax.persistence.criteria.CriteriaBuilder;

public abstract class ParameterFilter extends Filter {
    
    String param=null;

    public ParameterFilter(CriteriaBuilder cb) {
        super(cb);
    }
    
    public abstract void setParameter(String param) ;

    @Override
    public abstract String getQuery() ;

}
