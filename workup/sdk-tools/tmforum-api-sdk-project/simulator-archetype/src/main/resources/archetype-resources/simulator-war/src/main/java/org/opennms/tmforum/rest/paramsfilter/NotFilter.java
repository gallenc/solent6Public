#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import javax.persistence.criteria.CriteriaBuilder;

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

}
