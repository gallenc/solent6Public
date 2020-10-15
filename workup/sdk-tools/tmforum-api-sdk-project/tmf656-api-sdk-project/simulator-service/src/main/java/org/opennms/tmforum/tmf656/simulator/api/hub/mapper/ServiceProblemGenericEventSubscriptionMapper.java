package org.opennms.tmforum.tmf656.simulator.api.hub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;

@Mapper
public interface ServiceProblemGenericEventSubscriptionMapper {
    
    ServiceProblemGenericEventSubscriptionMapper INSTANCE = Mappers.getMapper( ServiceProblemGenericEventSubscriptionMapper.class );

    GenericEventSubscription eventSubscriptionToGenericEventSubscription(EventSubscription eventSubscription);
    
    EventSubscription genericEventSubscriptionTo(GenericEventSubscription genericEventSubscription);

}
