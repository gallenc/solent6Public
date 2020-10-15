#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.api.rest;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.opennms.tmforum.tmf650.api.GenericHubApiService;
import org.opennms.tmforum.tmf650.api.GenericListenerApiService;
import org.opennms.tmforum.tmf650.model.GenericNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package}.${tmfSpecPackageName}.swagger.api.ListenerApiService;
import ${package}.${tmfSpecPackageName}.swagger.api.NotFoundException;
import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import ${package}.${tmfSpecPackageName}.swagger.model.EventSubscription;
import ${package}.${tmfSpecPackageName}.swagger.model.ServiceProblemAttributeValueChangeNotification;

import io.swagger.annotations.ApiParam;

@Path("/generic-listener")
@Consumes({ "application/json;charset=utf-8" })
@Produces({ "application/json;charset=utf-8" })
@io.swagger.annotations.Api(description = "generic the listener API")
@javax.inject.Named
public class GenericListenerApi {
    private static Logger LOG = LoggerFactory.getLogger(GenericListenerApi.class);

    @javax.inject.Inject
    private GenericListenerApiService delegate = null;

    // additional method to allow browser to test if server running
    @GET
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    public Response simpleResponse(@Context SecurityContext securityContext,
            @Context javax.ws.rs.core.UriInfo uriInfo) {
        LOG.debug("GenericListenerApi simpleResponse() called delegate=" + delegate);
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity("{ ${symbol_escape}"message${symbol_escape}" : ${symbol_escape}"api server working${symbol_escape}" }")
                .build();
    }

    @POST
    @Path("/notification")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @io.swagger.annotations.ApiOperation(value = "Client listener for generic events", notes = "NOTE This is not part of the current spec but it defines a listener to listen for any events (GenericEvents) and not just specific event types", response = EventSubscription.class, tags = {
            "notification listeners (client side)", })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "Notified", response = EventSubscription.class),

            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 409, message = "Conflict", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    public Response listenToGenericNotification(
            @ApiParam(value = "The event data", required = true) GenericNotification data,
            @Context SecurityContext securityContext, @Context javax.ws.rs.core.UriInfo uriInfo)
            throws NotFoundException {
        return delegate.listenToGenericNotification(data, securityContext, uriInfo);
    }

}