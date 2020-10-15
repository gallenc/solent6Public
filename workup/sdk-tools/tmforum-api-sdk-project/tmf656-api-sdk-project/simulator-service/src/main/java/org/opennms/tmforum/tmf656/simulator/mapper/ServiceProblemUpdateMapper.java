package org.opennms.tmforum.tmf656.simulator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemUpdate;
import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;

@Mapper
public interface ServiceProblemUpdateMapper {
    
    ServiceProblemUpdateMapper INSTANCE = Mappers.getMapper( ServiceProblemUpdateMapper.class );

    ServiceProblemEntity serviceProblemUpdateToServiceProblemEntity(ServiceProblemUpdate serviceProblemUpdate);

    ServiceProblemUpdate serviceProblemEntityToServiceProblemUpdate(ServiceProblemEntity serviceProblemEntity);
    
}
