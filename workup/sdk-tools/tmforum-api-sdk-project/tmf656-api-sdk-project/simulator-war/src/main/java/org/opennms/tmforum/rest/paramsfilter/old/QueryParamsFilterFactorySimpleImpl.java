package org.opennms.tmforum.rest.paramsfilter.old;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

public class QueryParamsFilterFactorySimpleImpl implements QueryParamsFilterFactory{
    
    CriteriaBuilder criteriaBuilder=null;

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }
    
    List<ParameterValueFilterPair> parameterValueFilterPairList = new ArrayList<ParameterValueFilterPair>();

    @Override
    public ParameterValueFilterPair getNewParameterValueFilterPair(String parameterName) {
        
        ParameterFilter parameterFilter = new ParameterFilterSimpleImpl(criteriaBuilder);
        parameterFilter.setParameter(parameterName);
        
        ValueFilter valueFilter = new ValueFilterSimpleImpl(criteriaBuilder);
        
        ParameterValueFilterPair parameterValueFilterPair =  new ParameterValueFilterPair(parameterFilter, valueFilter);
        
        parameterValueFilterPairList.add(parameterValueFilterPair);
        
        return parameterValueFilterPair;
       
    }

    @Override
    public EqualsFilter getNewEqualsFilter(Filter childFilterA, Filter childFilterB) {

        return new EqualsFilter(criteriaBuilder, childFilterA, childFilterB);
    }

    @Override
    public GreaterThanEqualsFilter getNewGreaterThanEqualsFilter(Filter childFilterA, Filter childFilterB) {

        return new GreaterThanEqualsFilter(criteriaBuilder, childFilterA, childFilterB);
    }

    @Override
    public GreaterThanFilter getNewGreaterThanFilter(Filter childFilterA, Filter childFilterB) {

        return new GreaterThanFilter(criteriaBuilder, childFilterA, childFilterB);
    }

    @Override
    public LessThanEqualsFilter getNewLessThanEqualsFilter(Filter childFilterA, Filter childFilterB) {

        return new LessThanEqualsFilter(criteriaBuilder, childFilterA, childFilterB);
    }

    @Override
    public LessThanFilter getNewLessThanFilter(Filter childFilterA, Filter childFilterB) {

        return new LessThanFilter(criteriaBuilder, childFilterA, childFilterB);
    }

    @Override
    public NotFilter getNewNotFilter(Filter childFilter) {

        return new NotFilter(criteriaBuilder, childFilter);
    }

    @Override
    public OrFilter getNewOrFilter(Filter... childFilters) {

        return new OrFilter(criteriaBuilder, childFilters);
    }

    @Override
    public AndFilter getNewAndFilter(Filter... childFilters) {

        return new AndFilter(criteriaBuilder, childFilters);
    }

    @Override
    public RegexFilter getNewRegexFilter(Filter childFilterA, ValueFilter childFilterB) {

        return new RegexFilter(criteriaBuilder, childFilterA, childFilterB);
    }

 

}
