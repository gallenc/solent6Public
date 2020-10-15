#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.criteria.CriteriaBuilder;

public class OrFilter extends Filter {

    List<Filter> filters = new ArrayList<Filter>();
    
    public OrFilter(CriteriaBuilder cb) {
        super(cb);
    }
    
    public OrFilter(CriteriaBuilder cb, Filter... childFilters) {
        super(cb);
        filters.addAll(Arrays.asList(childFilters));
    }

    public OrFilter add(Filter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public String getQuery() {
        String query="";
        
        if(filters.isEmpty()) return query;
                
        ListIterator<Filter> iterator = filters.listIterator();
        
        query+=" ("+ iterator.next().getQuery();
        
        while(iterator.hasNext()) {
            query+=" OR "+ iterator.next().getQuery();
        }
        
        query+=") ";
        
        return query;
    }

}
