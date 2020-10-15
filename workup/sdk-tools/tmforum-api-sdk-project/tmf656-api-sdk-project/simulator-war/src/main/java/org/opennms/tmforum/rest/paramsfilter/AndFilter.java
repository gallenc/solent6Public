package org.opennms.tmforum.rest.paramsfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class AndFilter extends Filter{
    
    List<Filter> filters = new ArrayList<Filter>();
    
    public AndFilter(CriteriaBuilder cb) {
        super(cb);
    }

    public AndFilter(CriteriaBuilder cb, Filter... childFilters) {
        super(cb);
        filters.addAll(Arrays.asList(childFilters));
    }

    public AndFilter add(Filter filter) {
        filters.add(filter);
        return this;
    }


    @Override
    public String getQuery() {
        String query="";
        
        if(filters.isEmpty()) return query;
                
        ListIterator<Filter> iterator = filters.listIterator();
        
        query+="( "+ iterator.next().getQuery();
        
        while(iterator.hasNext()) {
            query+=" AND "+ iterator.next().getQuery();
        }
        
        query+=" )";
        
        return query;
    }
    
    


}
