#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemUpdate;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;

@Mapper
public interface ServiceProblemUpdateMapper {
    
    ServiceProblemUpdateMapper INSTANCE = Mappers.getMapper( ServiceProblemUpdateMapper.class );

    ServiceProblemEntity serviceProblemUpdateToServiceProblemEntity(ServiceProblemUpdate serviceProblemUpdate);

    ServiceProblemUpdate serviceProblemEntityToServiceProblemUpdate(ServiceProblemEntity serviceProblemEntity);
    
}
