#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import javax.persistence.criteria.CriteriaBuilder;

public class LessThanFilter extends Filter{

    Filter childFilterA;
    Filter childFilterB;

    public LessThanFilter(CriteriaBuilder cb, Filter childFilterA, Filter childFilterB) {
        super(cb);
        this.childFilterA=childFilterA;
        this.childFilterB=childFilterB;
    }

    @Override
    public String getQuery() {
        
        return "("+ childFilterA.getQuery() +" < "+ childFilterB.getQuery()+" )";
    }

}
