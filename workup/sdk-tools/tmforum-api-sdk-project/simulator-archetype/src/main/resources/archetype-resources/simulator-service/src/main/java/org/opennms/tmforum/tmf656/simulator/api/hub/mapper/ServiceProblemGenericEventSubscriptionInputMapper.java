#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.hub.mapper;

import org.mapstruct.factory.Mappers;
import ${package}.${tmfSpecPackageName}.swagger.model.EventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.mapstruct.Mapper;


@Mapper
public interface ServiceProblemGenericEventSubscriptionInputMapper {
    
    ServiceProblemGenericEventSubscriptionInputMapper INSTANCE = Mappers.getMapper( ServiceProblemGenericEventSubscriptionInputMapper.class );

    GenericEventSubscriptionInput eventSubscriptionInputToGenericEventSubscriptionInput(EventSubscriptionInput eventSubscriptionInput);
    
    EventSubscriptionInput genericEventSubscriptionInputTo(GenericEventSubscriptionInput genericEventSubscriptionInput);
    
}
