#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.jpa.json;

import javax.persistence.Converter;

@Converter
public class ObjectListConverter extends BaseListConverter<Object> {

}
