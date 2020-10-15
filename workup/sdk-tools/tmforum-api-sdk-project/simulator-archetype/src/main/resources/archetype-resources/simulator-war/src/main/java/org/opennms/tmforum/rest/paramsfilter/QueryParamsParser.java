#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.rest.paramsfilter;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

public class QueryParamsParser {

    // @Inject
    QueryParamsFilterFactory queryParamsFilterFactory = new QueryParamsFilterFactorySimpleImpl();

    public void setQueryParamsFilterFactory(QueryParamsFilterFactory queryParamsFilterFactory) {
        this.queryParamsFilterFactory = queryParamsFilterFactory;
    }

    /*
     * 
     * .gt .gte .lt .lte .regex ; or
     */
    public Filter parse(MultivaluedMap<String, String> mvMap) {

        AndFilter andFilter = queryParamsFilterFactory.getNewAndFilter();

        for (String param : mvMap.keySet()) {
            List<String> values = mvMap.get(param);
            String paramName = null;

            if (param.endsWith(".gt")) {
                paramName = param.substring(0, param.lastIndexOf(".gt"));

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {

                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter gtFilter = queryParamsFilterFactory.getNewGreaterThanFilter(parameterFilter, valueFilter);
                        orFilter.add(gtFilter);
                    }
                }

                andFilter.add(orFilter);

            } else if (param.endsWith(".gte")) {
                paramName = param.substring(0, param.lastIndexOf(".gte"));

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {
                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter gteFilter = queryParamsFilterFactory.getNewGreaterThanEqualsFilter(parameterFilter, valueFilter);
                        orFilter.add(gteFilter);
                    }

                }

                andFilter.add(orFilter);

            } else if (param.endsWith(".lt")) {
                paramName = param.substring(0, param.lastIndexOf(".lt"));

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {

                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter ltFilter = queryParamsFilterFactory.getNewLessThanFilter(parameterFilter, valueFilter);
                        orFilter.add(ltFilter);
                    }

                }

                andFilter.add(orFilter);

            } else if (param.endsWith(".lte")) {
                paramName = param.substring(0, param.lastIndexOf(".lte"));

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {

                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter lteFilter = queryParamsFilterFactory.getNewLessThanEqualsFilter(parameterFilter, valueFilter);
                        orFilter.add(lteFilter);
                    }

                }

                andFilter.add(orFilter);

            } else if (param.endsWith(".regex")) {
                paramName = param.substring(0, param.lastIndexOf(".regex"));

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {

                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter regexFilter = queryParamsFilterFactory.getNewRegexFilter(parameterFilter, valueFilter);
                        orFilter.add(regexFilter);
                    }

                }

                andFilter.add(orFilter);

            } else {
                // simple equals comparison
                // all values are ored in this case
                paramName = param;

                OrFilter orFilter = queryParamsFilterFactory.getNewOrFilter();
                for (String value : values) {

                    ParameterValueFilterPair parameterValueFilterPair = queryParamsFilterFactory
                            .getNewParameterValueFilterPair(paramName);
                    ParameterFilter parameterFilter = parameterValueFilterPair.getParameterFilter();

                    String[] multiValue = value.split(",");
                    for (String val : multiValue) {
                        ValueFilter valueFilter = parameterValueFilterPair.getValueFilter();
                        valueFilter.setValue(val);

                        Filter eqFilter = queryParamsFilterFactory.getNewEqualsFilter(parameterFilter, valueFilter);
                        orFilter.add(eqFilter);
                    }
                }

                andFilter.add(orFilter);

            }

        }

        return andFilter;

    }

}
