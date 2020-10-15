#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.hub.impl;

import org.opennms.tmforum.tmf650.api.*;
import org.opennms.tmforum.tmf650.model.*;

import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl.HubApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import ${package}.${tmfSpecPackageName}.swagger.api.ApiResponseMessage;
import ${package}.${tmfSpecPackageName}.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.*;

@Named
public class GenericHubApiServiceImpl extends GenericHubApiService {
    private static Logger LOG = LoggerFactory.getLogger(GenericHubApiServiceImpl.class);

    @Inject
    NotificationDispatcher notificationDispatcher;

    @Override
    public Response registerListener(GenericEventSubscriptionInput data, SecurityContext securityContext,
            javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {

        try {

            LOG.debug("register listener called EventSubscriptionInput=" + data);
            
            GenericEventSubscription subscriptionDetails = notificationDispatcher.registerListener(data);
            
            // unique UUID created
//            String id = UUID.randomUUID().toString();
//            String query = data.getQuery();
//            String callback = data.getCallback();
//
//            GenericEventSubscription subscriptionDetails = new GenericEventSubscription();
//
//            subscriptionDetails.callback(callback);
//
//            subscriptionDetails.setId(id);
//
//            subscriptionDetails.setQuery(query);

            // return Response.status(javax.ws.rs.core.Response.Status.OK).build();

            return Response.ok().entity(subscriptionDetails).build();

        } catch (Exception ex) {
            LOG.error("POST /registerListener registerListener ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /registerListener registerListener error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

    @Override
    public Response unregisterListener(String id, SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo)
            throws NotFoundException {
        try {
            LOG.debug("unregister listener called id=" + id);
            
            notificationDispatcher.unregisterListener(id);

            return Response.status(204).build();
        } catch (Exception ex) {
            LOG.error("POST /unregisterListener unregisterListener ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /unregisterListener unregisterListener error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }
    }
}
