package org.opennms.tmforum.tmf656.simulator.api.hub.mapper;

import org.mapstruct.factory.Mappers;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.mapstruct.Mapper;


@Mapper
public interface ServiceProblemGenericEventSubscriptionInputMapper {
    
    ServiceProblemGenericEventSubscriptionInputMapper INSTANCE = Mappers.getMapper( ServiceProblemGenericEventSubscriptionInputMapper.class );

    GenericEventSubscriptionInput eventSubscriptionInputToGenericEventSubscriptionInput(EventSubscriptionInput eventSubscriptionInput);
    
    EventSubscriptionInput genericEventSubscriptionInputTo(GenericEventSubscriptionInput genericEventSubscriptionInput);
    
}
