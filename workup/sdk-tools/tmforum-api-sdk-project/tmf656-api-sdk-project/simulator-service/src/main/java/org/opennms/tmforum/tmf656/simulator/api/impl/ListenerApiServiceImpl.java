package org.opennms.tmforum.tmf656.simulator.api.impl;

import org.opennms.tmforum.swagger.tmf656.swagger.api.*;
import org.opennms.tmforum.swagger.tmf656.swagger.model.*;

import org.opennms.tmforum.swagger.tmf656.swagger.model.Error;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventSubscription;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemAttributeValueChangeNotification;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemCreateNotification;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemInformationRequiredNotification;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemStateChangeNotification;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.tmf650.listener.impl.GenericListenerApiServiceImpl;
import org.opennms.tmforum.tmf650.listener.impl.GenericNotificationSubscriber;
import org.opennms.tmforum.tmf650.model.GenericEvent;
import org.opennms.tmforum.tmf650.model.GenericNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.inject.Inject;
import javax.validation.constraints.*;
//@javax.annotation.Generated(value = "org.opennms.tmforum.swagger.patch.JavaJerseyServerCodegen", date = "2020-06-19T15:51:51.633+01:00")

// NOTE this class has been modified by maven-replacer-plugin from swagger generated class to allow injection
@javax.inject.Named
public class ListenerApiServiceImpl extends ListenerApiService {
    private static Logger LOG = LoggerFactory.getLogger( ListenerApiServiceImpl.class);
    
    @Inject
    GenericNotificationSubscriber genericNotificationSubscriber;

    @Override
    public Response listenToServiceProblemAttributeValueChangeNotification(ServiceProblemAttributeValueChangeNotification data, SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {
        try {
            LOG.debug("listenToServiceProblemAttributeValueChangeNotification called for Notification=" + data);

            if (genericNotificationSubscriber != null) {

                // map notification to generic notification
                ObjectMapper om = NewJacksonFeature.getObjectMapper();

                JsonNode jsonNode = om.valueToTree(data.getEvent());

                GenericNotification genericNotification = new GenericNotification();
                genericNotification.setEventType(data.getEventType());
                
                genericNotification.setEvent(jsonNode);

                LOG.debug("sending notification to listeners=");
                genericNotificationSubscriber.onNotification(genericNotification);
            }

            return Response.ok().build();
            
        } catch (Exception ex) {
            LOG.error("POST /serviceProblemAttributeValueChangeNotification listenToServiceProblemAttributeValueChangeNotification  ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblemAttributeValueChangeNotification listenToServiceProblemAttributeValueChangeNotification  error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }
    }

    @Override
    public Response listenToServiceProblemCreateNotification(ServiceProblemCreateNotification data,
            SecurityContext securityContext, UriInfo uriInfo) throws NotFoundException {
        try {
            LOG.debug("listenToServiceProblemCreateNotification called for Notification=" + data);

            if (genericNotificationSubscriber != null) {

                // map notification to generic notification
                ObjectMapper om = NewJacksonFeature.getObjectMapper();

                JsonNode jsonNode = om.valueToTree(data.getEvent());

                GenericNotification genericNotification = new GenericNotification();
                genericNotification.setEventType(data.getEventType());
                genericNotification.setEvent(jsonNode);

                LOG.debug("sending notification to listeners=");
                genericNotificationSubscriber.onNotification(genericNotification);
            }

            return Response.ok().build();
            
        } catch (Exception ex) {
            LOG.error("POST /serviceProblemCreateNotification listenToServiceProblemCreateNotification  ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblemCreateNotification listenToServiceProblemCreateNotification  error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }
    }

    @Override
    public Response listenToServiceProblemInformationRequiredNotification(
            ServiceProblemInformationRequiredNotification data, SecurityContext securityContext, UriInfo uriInfo)
            throws NotFoundException {
        try {
            LOG.debug("listenToServiceProblemInformationRequiredNotification called for Notification=" + data);

            if (genericNotificationSubscriber != null) {

                // map notification to generic notification
                ObjectMapper om = NewJacksonFeature.getObjectMapper();

                JsonNode jsonNode = om.valueToTree(data.getEvent());

                GenericNotification genericNotification = new GenericNotification();
                genericNotification.setEventType(data.getEventType());
                
                genericNotification.setEvent(jsonNode);

                LOG.debug("sending notification to listeners=");
                genericNotificationSubscriber.onNotification(genericNotification);
            }

            return Response.ok().build();
            
        } catch (Exception ex) {
            LOG.error("POST /serviceProblemInformationRequiredNotification listenToServiceProblemInformationRequiredNotification  ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblemInformationRequiredNotification listenToServiceProblemInformationRequiredNotification  error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

    @Override
    public Response listenToServiceProblemStateChangeNotification(ServiceProblemStateChangeNotification data,
            SecurityContext securityContext, UriInfo uriInfo) throws NotFoundException {
        try {
            LOG.debug("listenToServiceProblemStateChangeNotification called for Notification=" + data);

            if (genericNotificationSubscriber != null) {

                // map notification to generic notification
                ObjectMapper om = NewJacksonFeature.getObjectMapper();

                JsonNode jsonNode = om.valueToTree(data.getEvent());

                GenericNotification genericNotification = new GenericNotification();
                genericNotification.setEventType(data.getEventType());
                
                genericNotification.setEvent(jsonNode);

                LOG.debug("sending notification to listeners=");
                genericNotificationSubscriber.onNotification(genericNotification);
            }

            return Response.ok().build();
            
        } catch (Exception ex) {
            LOG.error("POST /serviceProblemStateChangeNotification listenToServiceProblemStateChangeNotification  ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblemStateChangeNotification listenToServiceProblemStateChangeNotification  error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }
    }

}
