#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;

public  class ParameterFilterSimpleImpl extends ParameterFilter {
    
    public ParameterFilterSimpleImpl(CriteriaBuilder cb) {
        super(cb);
    }

    String param=null;
    
    @Override
    public void setParameter(String param) {
        Objects.requireNonNull(param);
        this.param=param;
    }

    @Override
    public String getQuery() {
        return param;
    };

}
