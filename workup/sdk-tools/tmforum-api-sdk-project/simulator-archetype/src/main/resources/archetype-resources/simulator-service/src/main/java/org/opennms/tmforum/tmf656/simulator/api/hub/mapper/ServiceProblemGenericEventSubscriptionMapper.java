#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.hub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${package}.${tmfSpecPackageName}.swagger.model.EventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;

@Mapper
public interface ServiceProblemGenericEventSubscriptionMapper {
    
    ServiceProblemGenericEventSubscriptionMapper INSTANCE = Mappers.getMapper( ServiceProblemGenericEventSubscriptionMapper.class );

    GenericEventSubscription eventSubscriptionToGenericEventSubscription(EventSubscription eventSubscription);
    
    EventSubscription genericEventSubscriptionTo(GenericEventSubscription genericEventSubscription);

}
