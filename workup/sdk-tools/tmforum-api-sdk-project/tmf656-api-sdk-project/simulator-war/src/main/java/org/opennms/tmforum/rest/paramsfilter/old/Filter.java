package org.opennms.tmforum.rest.paramsfilter.old;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public abstract class Filter {
 
    protected CriteriaBuilder cb;
    
    private Filter childFilter=null;
    
    protected Filter(CriteriaBuilder cb) {
        this.cb=cb;
    }
    
    public Filter(CriteriaBuilder cb, Filter childFilter) {
        this.cb=cb;
        this.childFilter = childFilter;
    }
    
    
    public String getQuery() {
        return (childFilter==null) ? "" :childFilter.getQuery() ;
    }
    
    
    public abstract Expression  getExpression() ;

}
