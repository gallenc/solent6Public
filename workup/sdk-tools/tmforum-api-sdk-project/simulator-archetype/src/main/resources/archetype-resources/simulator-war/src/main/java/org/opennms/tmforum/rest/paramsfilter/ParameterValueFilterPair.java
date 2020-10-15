#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

public class ParameterValueFilterPair {
    
    private ParameterFilter parameterFilter;
    
    private ValueFilter valueFilter;
    

    public ParameterValueFilterPair(ParameterFilter parameterFilter, ValueFilter valueFilter) {
        super();
        this.parameterFilter = parameterFilter;
        this.valueFilter = valueFilter;
    }

    public ParameterFilter getParameterFilter() {
        return parameterFilter;
    }

    public ValueFilter getValueFilter() {
        return valueFilter;
    }

}
