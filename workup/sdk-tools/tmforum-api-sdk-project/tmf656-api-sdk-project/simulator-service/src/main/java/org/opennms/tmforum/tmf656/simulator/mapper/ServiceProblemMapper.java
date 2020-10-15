package org.opennms.tmforum.tmf656.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblem;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemUpdate;
import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;

@Mapper( nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface ServiceProblemMapper {
    
    ServiceProblemMapper INSTANCE = Mappers.getMapper( ServiceProblemMapper.class );

    ServiceProblemEntity serviceProblemToServiceProblemEntity(ServiceProblem serviceProblem);

    ServiceProblem serviceProblemEntityToServiceProblem(ServiceProblemEntity serviceProblemEntity);
    
    ServiceProblemEntity serviceProblemUpdateServiceProblemEntity(ServiceProblemUpdate serviceProblemUpdate, @MappingTarget ServiceProblemEntity serviceProblemEntity);
    
}
