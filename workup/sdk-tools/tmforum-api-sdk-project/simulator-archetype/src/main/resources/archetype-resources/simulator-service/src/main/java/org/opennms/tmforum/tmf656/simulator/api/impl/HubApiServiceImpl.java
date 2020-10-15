#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl;

import ${package}.${tmfSpecPackageName}.swagger.api.*;
import ${package}.${tmfSpecPackageName}.swagger.model.*;

import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import ${package}.${tmfSpecPackageName}.swagger.model.EventSubscription;
import ${package}.${tmfSpecPackageName}.swagger.model.EventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.tmf650.api.GenericHubApiService;
import org.opennms.tmforum.tmf650.hub.impl.GenericHubApiServiceImpl;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.hub.mapper.ServiceProblemGenericEventSubscriptionInputMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemCreateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import ${package}.${tmfSpecPackageName}.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.mapstruct.factory.Mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;
import javax.validation.constraints.*;

// NOTE this class has been modified by maven-replacer-plugin from swagger generated class to allow injection
@javax.inject.Named
public class HubApiServiceImpl extends HubApiService {
    private static Logger LOG = LoggerFactory.getLogger(HubApiServiceImpl.class);

    // statically finds the fully qualificed name of this class
    private static String apiServiceImplClassName = java.lang.invoke.MethodHandles.lookup().lookupClass().getName();

    @Inject
    private GenericHubApiServiceImpl genericHub;
    
    @Override
    public Response registerListener(EventSubscriptionInput eventSubscriptionInput, SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {
        
        LOG.debug("register listener called EventSubscriptionInput="+eventSubscriptionInput);
        
        GenericEventSubscriptionInput genericEventSubscriptionInput = 
                Mappers.getMapper( ServiceProblemGenericEventSubscriptionInputMapper.class )
                .eventSubscriptionInputToGenericEventSubscriptionInput(eventSubscriptionInput);
        
        // note Returning unmapped class but OK as json is the same
        return genericHub.registerListener(genericEventSubscriptionInput, securityContext, uriInfo);

    }
    
    @Override
    public Response unregisterListener(String id, SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {
        LOG.debug("unregister listener called id="+id);
        
        // note Returning unmapped class but OK as json is the same
        return genericHub.unregisterListener(id, securityContext, uriInfo);

    }
}
