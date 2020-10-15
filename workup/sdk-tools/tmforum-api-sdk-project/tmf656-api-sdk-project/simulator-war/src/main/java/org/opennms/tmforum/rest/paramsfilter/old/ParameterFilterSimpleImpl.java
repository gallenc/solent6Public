package org.opennms.tmforum.rest.paramsfilter.old;

import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

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
    }

    @Override
    public Predicate getExpression() {
        // TODO Auto-generated method stub
        return null;
    };

}
