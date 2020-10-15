#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblem;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemUpdate;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;

@Mapper( nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface ServiceProblemMapper {
    
    ServiceProblemMapper INSTANCE = Mappers.getMapper( ServiceProblemMapper.class );

    ServiceProblemEntity serviceProblemToServiceProblemEntity(ServiceProblem serviceProblem);

    ServiceProblem serviceProblemEntityToServiceProblem(ServiceProblemEntity serviceProblemEntity);
    
    ServiceProblemEntity serviceProblemUpdateServiceProblemEntity(ServiceProblemUpdate serviceProblemUpdate, @MappingTarget ServiceProblemEntity serviceProblemEntity);
    
}
