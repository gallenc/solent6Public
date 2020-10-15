#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemCreate;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;

@Mapper
public interface ServiceProblemCreateMapper {
    
    ServiceProblemCreateMapper INSTANCE = Mappers.getMapper( ServiceProblemCreateMapper.class );

    ServiceProblemEntity serviceProblemCreateToServiceProblemEntity(ServiceProblemCreate serviceProblemCreate);

    ServiceProblemCreate serviceProblemEntityToServiceProblemCreate(ServiceProblemEntity serviceProblemEntity);
    
}
