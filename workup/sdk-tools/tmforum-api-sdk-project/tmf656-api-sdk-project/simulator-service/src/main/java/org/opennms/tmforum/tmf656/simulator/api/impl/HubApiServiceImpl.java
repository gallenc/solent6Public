package org.opennms.tmforum.tmf656.simulator.api.impl;

import org.opennms.tmforum.swagger.tmf656.swagger.api.*;
import org.opennms.tmforum.swagger.tmf656.swagger.model.*;

import org.opennms.tmforum.swagger.tmf656.swagger.model.Error;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventSubscription;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventSubscriptionInput;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.tmf650.api.GenericHubApiService;
import org.opennms.tmforum.tmf650.hub.impl.GenericHubApiServiceImpl;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf656.simulator.api.hub.mapper.ServiceProblemGenericEventSubscriptionInputMapper;
import org.opennms.tmforum.tmf656.simulator.mapper.ServiceProblemCreateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import org.opennms.tmforum.swagger.tmf656.swagger.api.NotFoundException;

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
    public Response registerListener(EventSubscriptionInput eventSubscriptionInput, SecurityContext securityContext,
            javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {

        LOG.debug("register listener called EventSubscriptionInput=" + eventSubscriptionInput);

        GenericEventSubscriptionInput genericEventSubscriptionInput = Mappers
                .getMapper(ServiceProblemGenericEventSubscriptionInputMapper.class)
                .eventSubscriptionInputToGenericEventSubscriptionInput(eventSubscriptionInput);

        // note Returning unmapped class but OK as json is the same
        try {
            return genericHub.registerListener(genericEventSubscriptionInput, securityContext, uriInfo);
        } catch (org.opennms.tmforum.tmf650.api.NotFoundException e) {
            throw new NotFoundException(0, e.getMessage());
        }

    }

    @Override
    public Response unregisterListener(String id, SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo)
            throws NotFoundException {
        LOG.debug("unregister listener called id=" + id);

        // note Returning unmapped class but OK as json is the same
        try {
            return genericHub.unregisterListener(id, securityContext, uriInfo);
        } catch (org.opennms.tmforum.tmf650.api.NotFoundException e) {
            throw new NotFoundException(0, e.getMessage());
        }
    }
}
