#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.jpa.${tmfSpecPackageName}.json;

import javax.persistence.Converter;

import org.opennms.tmforum.jpa.json.BaseListConverter;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemRef;

@Converter
public class ServiceProblemRefListConverter extends BaseListConverter<ServiceProblemRef> {

}
