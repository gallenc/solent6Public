#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter.old;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class NotFilter extends Filter {

    private Filter childFilter = null;

    public NotFilter(CriteriaBuilder cb, Filter childFilter) {
        super(cb);
        this.childFilter = childFilter;
    }

    @Override
    public String getQuery() {

        String query = " NOT ( " + childFilter.getQuery() + " ) ";
        return query;

    }
    
    @Override
    public Predicate getExpression() {

        return cb.not(childFilter.getExpression());
        
    }

}
