package org.opennms.tmforum.rest.paramsfilter.old;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class LessThanEqualsFilter extends Filter{

    Filter childFilterA;
    Filter childFilterB;

    public LessThanEqualsFilter(CriteriaBuilder cb, Filter childFilterA, Filter childFilterB) {
        super(cb);
        this.childFilterA=childFilterA;
        this.childFilterB=childFilterB;
    }

    @Override
    public String getQuery() {
        return "("+ childFilterA.getQuery() +" <= "+ childFilterB.getQuery()+" )";
    }

    @Override
    public Predicate getExpression() {

        return cb.le(childFilterA.getExpression(), childFilterB.getExpression());
    }

}
