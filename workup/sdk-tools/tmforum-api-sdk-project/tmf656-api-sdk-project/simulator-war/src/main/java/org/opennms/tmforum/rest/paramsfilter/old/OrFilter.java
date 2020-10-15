package org.opennms.tmforum.rest.paramsfilter.old;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

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
    
    @Override
    public Predicate getExpression() {
        
        List<Expression> expressions = new ArrayList<Expression>();
        for(Filter f:filters) {
            expressions.add(f.getExpression());
        }
        
        return cb.or(expressions.toArray(new Predicate[0]));
        
    }

}
