#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

public interface QueryParamsFilterFactory {
    
    ParameterValueFilterPair getNewParameterValueFilterPair(String parameterName);
    
    EqualsFilter getNewEqualsFilter(Filter childFilterA, Filter childFilterB);
    
    GreaterThanEqualsFilter getNewGreaterThanEqualsFilter(Filter childFilterA, Filter childFilterB);
    
    GreaterThanFilter getNewGreaterThanFilter(Filter childFilterA, Filter childFilterB);
    
    LessThanEqualsFilter getNewLessThanEqualsFilter(Filter childFilterA, Filter childFilterB);
    
    LessThanFilter getNewLessThanFilter(Filter childFilterA, Filter childFilterB);
    
    NotFilter getNewNotFilter(Filter childFilter);
    
    RegexFilter getNewRegexFilter(Filter childFilterA, ValueFilter childFilterB);
    
    OrFilter getNewOrFilter(Filter... childFilters);
    
    AndFilter getNewAndFilter(Filter... childFilters);
    
}
