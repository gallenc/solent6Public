#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter.old;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class EqualsFilter extends Filter {

    Filter childFilterA;
    Filter childFilterB;

    public EqualsFilter(CriteriaBuilder cb, Filter childFilterA, Filter childFilterB) {
        super(cb);
        this.childFilterA = childFilterA;
        this.childFilterB = childFilterB;
    }

    @Override
    public String getQuery() {

        return "(" + childFilterA.getQuery() + " = " + childFilterB.getQuery() + " )";
    }

    @Override
    public Predicate getExpression() {

        return cb.equal(childFilterA.getExpression(), childFilterB.getExpression());
        
    }

}
