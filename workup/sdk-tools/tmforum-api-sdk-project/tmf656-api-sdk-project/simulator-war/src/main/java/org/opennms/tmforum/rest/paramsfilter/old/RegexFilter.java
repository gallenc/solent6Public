package org.opennms.tmforum.rest.paramsfilter.old;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class RegexFilter extends Filter{

    Filter childFilterA;
    ValueFilter childFilterB;

    public RegexFilter(CriteriaBuilder cb, Filter childFilterA, ValueFilter childFilterB) {
        super(cb);
        this.childFilterA=childFilterA;
        this.childFilterB=childFilterB;
    }

    @Override
    public String getQuery() {
        
        // TODO PROPER REGEX
        
        return "("+ childFilterA.getQuery() +" LIKE "+ childFilterB.getQuery()+" )";
    }

    @Override
    public Predicate getExpression() {
        
        // TODO PROPER REGEX
        
        return cb.like(childFilterA.getExpression(), childFilterB.getExpression());
    }

}
