package org.opennms.tmforum.rest.paramsfilter;

import javax.persistence.criteria.CriteriaBuilder;

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

}
