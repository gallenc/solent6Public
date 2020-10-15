package org.opennms.tmforum.jpa.tmf656.json;

import javax.persistence.Converter;

import org.opennms.tmforum.jpa.json.BaseListConverter;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemRef;

@Converter
public class ServiceProblemRefListConverter extends BaseListConverter<ServiceProblemRef> {

}
