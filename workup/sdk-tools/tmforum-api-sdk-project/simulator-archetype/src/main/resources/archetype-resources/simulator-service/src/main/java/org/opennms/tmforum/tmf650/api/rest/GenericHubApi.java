#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.api.rest;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

//import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import org.opennms.tmforum.tmf650.api.GenericHubApiService;
import org.opennms.tmforum.tmf650.model.GenericEventSubscription;
import org.opennms.tmforum.tmf650.model.GenericEventSubscriptionInput;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl.HubApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.List;

import ${package}.${tmfSpecPackageName}.swagger.api.ApiResponseMessage;
import ${package}.${tmfSpecPackageName}.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/generic-hub")
@Consumes({ "application/json;charset=utf-8" })
@Produces({ "application/json;charset=utf-8" })
@io.swagger.annotations.Api(description = "the hub API")

@javax.inject.Named
public class GenericHubApi {
    private static Logger LOG = LoggerFactory.getLogger(GenericHubApiService.class);

    @javax.inject.Inject
    private GenericHubApiService delegate = null;

    // additional method to allow browser to test if server running
    @GET
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    public Response simpleResponse(@Context SecurityContext securityContext, @Context javax.ws.rs.core.UriInfo uriInfo) {
        LOG.debug("GenericHubApi simpleResponse() called delegate=" + delegate);
        return Response.status(javax.ws.rs.core.Response.Status.OK).entity("{ ${symbol_escape}"message${symbol_escape}" : ${symbol_escape}"api server working${symbol_escape}" }").build();
    }

    @POST

    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @io.swagger.annotations.ApiOperation(value = "Register a listener", notes = "(Note - not part of Spec) Sets the communication endpoint address the service instance must use to deliver information about its health state, execution state, failures and metrics.", response = GenericEventSubscription.class, tags = {
            "events subscription", })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "Subscribed", response = GenericEventSubscription.class),

            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 409, message = "Conflict", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    public Response registerListener(
            @ApiParam(value = "Data containing the callback endpoint to deliver the information", required = true) GenericEventSubscriptionInput data,
            @Context SecurityContext securityContext, @Context javax.ws.rs.core.UriInfo uriInfo)
            throws NotFoundException {
        return delegate.registerListener(data, securityContext, uriInfo);
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json;charset=utf-8" })
    @Produces({ "application/json;charset=utf-8" })
    @io.swagger.annotations.ApiOperation(value = "Unregister a listener", notes = "Resets the communication endpoint address the service instance must use to deliver information about its health state, execution state, failures and metrics.", response = Void.class, tags = {
            "events subscription", })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 204, message = "Deleted", response = Void.class),

            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 405, message = "Method not allowed", response = Error.class),

            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    public Response unregisterListener(
            @ApiParam(value = "The id of the registered listener", required = true) @PathParam("id") String id,
            @Context SecurityContext securityContext, @Context javax.ws.rs.core.UriInfo uriInfo)
            throws NotFoundException {
        return delegate.unregisterListener(id, securityContext, uriInfo);
    }
}
