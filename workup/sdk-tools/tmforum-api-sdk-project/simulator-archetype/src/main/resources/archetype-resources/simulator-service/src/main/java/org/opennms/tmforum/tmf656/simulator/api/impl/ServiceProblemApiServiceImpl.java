#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl;

import ${package}.${tmfSpecPackageName}.swagger.api.*;
import ${package}.${tmfSpecPackageName}.swagger.model.*;

import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import org.opennms.tmforum.tmf650.hub.impl.GenericHubApiServiceImpl;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.${tmfSpecPackageName}.service.FieldFilter;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.dao.ServiceProblemRepository;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemCreateMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ${package}.${tmfSpecPackageName}.swagger.api.NotFoundException;

import java.io.InputStream;
import java.time.OffsetDateTime;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.*;

@Named
public class ServiceProblemApiServiceImpl extends ServiceProblemApiService {
    final static Logger LOG = LoggerFactory.getLogger(ServiceProblemApiServiceImpl.class);

    // TODO MAKE CONFIG OPTION
    public static final Integer DEFAULT_MAX_PAGE_LIMIT = 100; // maximum number of alarms returned if limit not
                                                              // specified

    @Inject
    private ServiceProblemRepository serviceProblemRepository;

    @Inject
    NotificationDispatcher notificationDispatcher;

    @Override
    @Transactional
    public Response createServiceProblem(ServiceProblemCreate serviceProblemCreate, SecurityContext securityContext,
            UriInfo uriInfo) throws NotFoundException {
        try {
            LOG.debug("POST /serviceProblem createServiceProblem called");

            // map swagger dto to jpa entity
            LOG.debug("serviceProblemCreate:" + serviceProblemCreate);

            // set initial creation time
            OffsetDateTime timeRaised = OffsetDateTime.now();
            serviceProblemCreate.setTimeRaised(timeRaised);

            // set initial status to Submitted
            serviceProblemCreate.setStatus(ServiceProblemStatus.Submitted.toString());
            serviceProblemCreate.setStatusChangeDate(timeRaised);
            serviceProblemCreate.setStatusChangeReason("initial creation");

            ServiceProblemEntity serviceProblemEntity = ServiceProblemCreateMapper.INSTANCE
                    .serviceProblemCreateToServiceProblemEntity(serviceProblemCreate);

            LOG.debug("persisting serviceProblemEntity:" + serviceProblemEntity);

            // persist jpa entity
            serviceProblemEntity = serviceProblemRepository.save(serviceProblemEntity);

            // map jpa entity to swagger dto
            ServiceProblem serviceProblem = ServiceProblemMapper.INSTANCE
                    .serviceProblemEntityToServiceProblem(serviceProblemEntity);

            // add absolute path href
            String idStr = serviceProblem.getId();
            String href = uriInfo.getAbsolutePath().toASCIIString();
            href = (href.endsWith("/")) ? (href + idStr) : (href + "/" + idStr);
            serviceProblem.setHref(href);

            // persist path href (done this way because we can't persist the href before we
            // know the id.
            serviceProblemEntity.setHref(href);
            serviceProblemEntity = serviceProblemRepository.save(serviceProblemEntity);

            LOG.debug("created service problem returning serviceProblem:" + serviceProblem);

            // service problem create event
            ServiceProblemCreateNotification notification = new ServiceProblemCreateNotification();
            ServiceProblemCreateEvent event = new ServiceProblemCreateEvent();
            event.setServiceProblem(serviceProblem);
            // TODO eventRepository.createEvent save changed event

            notification.setEvent(event);
            notificationDispatcher.sendNotification(notification);

            return Response.status(Response.Status.CREATED).entity(serviceProblem).build();

        } catch (Exception ex) {
            LOG.error("POST /serviceProblem createServiceProblem ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblem createServiceProblem error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

    @Override
    @Transactional
    public Response deleteServiceProblem(String idStr, SecurityContext securityContext, UriInfo uriInfo)
            throws NotFoundException {

        try {
            LOG.debug("DELETE /deleteServiceProblem/{id} called id=" + idStr);

            Long id = Long.parseLong(idStr);
            ApiResponseMessage apiResponseMessage;

            if (!serviceProblemRepository.existsById(id)) {
                LOG.debug("DELETE /deleteServiceProblem/{id} entity does not exist id=" + idStr);
                apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                        "DELETE /deleteServiceProblem/{id} not found id=" + idStr);
                return Response.status(Response.Status.NOT_FOUND).entity(apiResponseMessage).build();
            }

            // state change to cancelled
            ServiceProblemStateChangeNotification notification = new ServiceProblemStateChangeNotification();
            ServiceProblemStateChangeEvent event = new ServiceProblemStateChangeEvent();
            // note delted service problem so not using real service problem

            ServiceProblem serviceProblem = new ServiceProblem();
            serviceProblem.setId(idStr);

            // set resolution time
            OffsetDateTime resolutionDate = OffsetDateTime.now();
            serviceProblem.setResolutionDate(resolutionDate);
            serviceProblem.setTimeChanged(resolutionDate);

            // set status to Cancelled
            serviceProblem.setStatus(ServiceProblemStatus.Cancelled.toString());
            serviceProblem.setStatusChangeDate(resolutionDate);
            serviceProblem.setStatusChangeReason("problem deleted");

            event.setServiceProblem(serviceProblem);
            // TODO eventRepository.createEvent save changed event

            notification.setEvent(event);
            notificationDispatcher.sendNotification(notification);

            // map jpa entity to swagger dto
            serviceProblemRepository.deleteById(id);

            return Response.status(Response.Status.NO_CONTENT).build(); // NO_CONTENT = 204

        } catch (Exception ex) {
            LOG.error("POST /serviceProblem createServiceProblem ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /serviceProblem createServiceProblem error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }
    }

    @Override
    public Response listServiceProblem(String fields, Integer offset, Integer limit, SecurityContext securityContext,
            UriInfo uriInfo) throws NotFoundException {

        try {
            LOG.debug("GET /serviceProblem/ listServiceProblem called offset=" + offset + " limit=" + limit + " fields="
                    + fields);

            Pageable pageRequest;

            if (offset == null && limit == null) {
                pageRequest = PageRequest.of(0, DEFAULT_MAX_PAGE_LIMIT, Sort.by("id").ascending());
            } else {
                if (offset < 0 || limit < 1)
                    throw new IllegalArgumentException("offset (" + offset + ") must not be negative, limit(" + limit
                            + ")  must be greater than 0.");
                pageRequest = PageRequest.of(offset, limit, Sort.by("id").ascending());
            }

            // retrieve page of Entity objects
            Page<ServiceProblemEntity> serviceProblemEntities = serviceProblemRepository.findAll(pageRequest);

            // map to dto objects
            List<ServiceProblem> serviceProblems = new ArrayList<ServiceProblem>();
            for (ServiceProblemEntity serviceProblemEntity : serviceProblemEntities) {
                ServiceProblem serviceProblem = ServiceProblemMapper.INSTANCE
                        .serviceProblemEntityToServiceProblem(serviceProblemEntity);

                // add absolute path href
                String idStr = serviceProblem.getId();
                String href = uriInfo.getAbsolutePath().toASCIIString();
                href = (href.endsWith("/")) ? (href + idStr) : (href + "/" + idStr);
                serviceProblem.setHref(href);

                FieldFilter<ServiceProblem> fieldFilter = new FieldFilter<ServiceProblem>();

                serviceProblem = fieldFilter.filter(serviceProblem, fields, null);

                serviceProblems.add(serviceProblem);
            }

            return Response.status(Response.Status.OK).entity(serviceProblems).build();

        } catch (Exception ex) {
            LOG.error("GET /serviceProblem/id retrieveServiceProblem ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "GET /serviceProblem/id retrieveServiceProblem: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

    @Override
    @Transactional
    public Response patchServiceProblem(String idStr, ServiceProblemUpdate serviceProblemUpdate,
            SecurityContext securityContext, UriInfo uriInfo) throws NotFoundException {

        try {
            LOG.debug("PATCH /serviceProblem patchServiceProblem  called");

            LOG.debug("update id:" + idStr + " serviceProblemUpdate:" + serviceProblemUpdate);

            // find original serviceProblem if exists
            Long id = Long.parseLong(idStr);
            ApiResponseMessage apiResponseMessage;

            Optional<ServiceProblemEntity> spOptional = serviceProblemRepository.findById(id);
            if (!spOptional.isPresent()) {
                LOG.debug("PATCH /serviceProblem patchServiceProblem entity not found id=" + idStr);
                apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                        "PATCH /serviceProblem patchServiceProblem entity not found id=" + idStr);
                return Response.status(Response.Status.NOT_FOUND).entity(apiResponseMessage).build();
            }

            ServiceProblemEntity serviceProblemEntity = spOptional.get();
            LOG.debug("original serviceProblemEntity:" + serviceProblemEntity);

            // check new status
            String originalStatus = (serviceProblemEntity.getStatus() == null) ? "" : serviceProblemEntity.getStatus();
            String newStatus = serviceProblemUpdate.getStatus();
            
            // update fields which have been posted as not null
            serviceProblemEntity = ServiceProblemMapper.INSTANCE
                    .serviceProblemUpdateServiceProblemEntity(serviceProblemUpdate, serviceProblemEntity);
            
            // set time changed
            OffsetDateTime timeChanged = OffsetDateTime.now();
            serviceProblemEntity.setTimeChanged(timeChanged);
            
            // set status change time
            if ((newStatus!=null ) && (!originalStatus.equals(newStatus))) {
                LOG.debug("service problem id:" + idStr + " status changed: old status:" + originalStatus
                        + " new status:" + newStatus);
                serviceProblemEntity.setStatusChangeDate(timeChanged);
                if (ServiceProblemStatus.Resolved.name().equals(newStatus)) {
                    serviceProblemEntity.setResolutionDate(timeChanged);
                } else {
                    serviceProblemEntity.setResolutionDate(null);
                }
            }

            LOG.debug("persisting updated serviceProblemEntity:" + serviceProblemEntity);

            // persist jpa entity
            serviceProblemEntity = serviceProblemRepository.save(serviceProblemEntity);

            // map jpa entity to swagger dto
            ServiceProblem serviceProblem = ServiceProblemMapper.INSTANCE
                    .serviceProblemEntityToServiceProblem(serviceProblemEntity);

            // add absolute path href
            String href = uriInfo.getAbsolutePath().toASCIIString();
            href = (href.endsWith("/")) ? (href + idStr) : (href + "/" + idStr);
            serviceProblem.setHref(href);

            LOG.debug("returning updated serviceProblem:" + serviceProblem);

            // service problem AttributeValueChange event
            ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
            ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
            event.setServiceProblem(serviceProblem);
            // TODO eventRepository.createEvent save changed event

            notification.setEvent(event);
            notificationDispatcher.sendNotification(notification);

            return Response.status(Response.Status.OK).entity(serviceProblem).build();

        } catch (Exception ex) {
            LOG.error("PATCH /serviceProblem patchServiceProblem ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "PATCH /serviceProblem patchServiceProblem error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

    @Override
    public Response retrieveServiceProblem(String idStr, String fields, SecurityContext securityContext,
            UriInfo uriInfo) throws NotFoundException {

        try {
            LOG.debug("GET /serviceProblem/id retrieveServiceProblem called id=" + idStr + " fields=" + fields);

            Long id = Long.parseLong(idStr);
            ApiResponseMessage apiResponseMessage;

            Optional<ServiceProblemEntity> spOptional = serviceProblemRepository.findById(id);
            if (!spOptional.isPresent()) {
                LOG.debug("GET /serviceProblem/id retrieveServiceProblem entity not found id=" + idStr);
                apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                        "GET /serviceProblem/id retrieveServiceProblem entity not found id=" + idStr);
                return Response.status(Response.Status.NOT_FOUND).entity(apiResponseMessage).build();
            }

            ServiceProblemEntity serviceProblemEntity = spOptional.get();

            LOG.debug("retreived serviceProblemEntity:" + serviceProblemEntity);

            // map jpa entity to swagger dto
            ServiceProblem serviceProblem = ServiceProblemMapper.INSTANCE
                    .serviceProblemEntityToServiceProblem(serviceProblemEntity);

            // add absolute path href
            String href = uriInfo.getAbsolutePath().toASCIIString();
            serviceProblem.setHref(href);

            LOG.debug("mapped serviceProblem:" + serviceProblem);

            FieldFilter<ServiceProblem> fieldFilter = new FieldFilter<ServiceProblem>();

            serviceProblem = fieldFilter.filter(serviceProblem, fields, null);

            LOG.debug("field filtered serviceProblem:" + serviceProblem);

            return Response.status(Response.Status.CREATED).entity(serviceProblem).build();

        } catch (Exception ex) {
            LOG.error("GET /serviceProblem/id retrieveServiceProblem ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "GET /serviceProblem/id retrieveServiceProblem: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }
}
