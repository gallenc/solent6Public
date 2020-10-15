package org.opennms.tmforum.tmf656.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemCreate;
import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;

@Mapper
public interface ServiceProblemCreateMapper {
    
    ServiceProblemCreateMapper INSTANCE = Mappers.getMapper( ServiceProblemCreateMapper.class );

    ServiceProblemEntity serviceProblemCreateToServiceProblemEntity(ServiceProblemCreate serviceProblemCreate);

    ServiceProblemCreate serviceProblemEntityToServiceProblemCreate(ServiceProblemEntity serviceProblemEntity);
    
}
