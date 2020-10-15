#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl;

import ${package}.${tmfSpecPackageName}.swagger.api.*;
import ${package}.${tmfSpecPackageName}.swagger.model.*;

import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.dao.ServiceProblemRepository;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemCreateMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.InputStream;
import java.time.OffsetDateTime;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.*;

@javax.inject.Named
@Transactional
public class ProblemAcknowledgementApiServiceImpl extends ProblemAcknowledgementApiService {
    final static Logger LOG = LoggerFactory.getLogger(ProblemAcknowledgementApiServiceImpl.class);

    @Inject
    private ServiceProblemRepository serviceProblemRepository;

    @Inject
    NotificationDispatcher notificationDispatcher;

    @Override
    public Response createProblemAcknowledgement(ProblemAcknowledgementCreate problemAcknowledgement,
            SecurityContext securityContext, javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {

        try {
            LOG.debug("POST /problemAcknowledgement createProblemAcknowledgement called");

            LOG.debug("problemAcknowledgement:" + problemAcknowledgement);

            ProblemAcknowledgement problemAcknowledgementResponse = new ProblemAcknowledgement();
            

            if (problemAcknowledgement == null)
                throw new IllegalArgumentException("problemAcknowledgement cannot be null");

            List<ServiceProblemEntity> updateList = new ArrayList<ServiceProblemEntity>();
            for (ServiceProblemRef problemref : problemAcknowledgement.getProblem()) {
                Long id = null;
                try {
                    id = Long.parseLong(problemref.getId());
                } catch (Exception ex) {
                    throw new IllegalArgumentException("cannot parse problemref id:" + problemref.getId());
                }

                // find service problem to update
                Optional<ServiceProblemEntity> optionalspe = serviceProblemRepository.findById(id);
                if (optionalspe.isPresent()) {
                    updateList.add(optionalspe.get());

                } else {
                    LOG.debug("problemAcknowledgement cannot find service problem id:" + id);
                }

            }

            // update all found entities and send out events
            for (ServiceProblemEntity serviceProblemEntity : updateList) {
                serviceProblemEntity.setStatus(ServiceProblemStatus.Acknowledged.toString());
                serviceProblemEntity.setStatusChangeDate(OffsetDateTime.now());
                serviceProblemEntity.setStatusChangeReason("problem acknowledgement from api user");
                
                // persist acknowledged jpa entity
                serviceProblemEntity = serviceProblemRepository.save(serviceProblemEntity);

                // generate acknowledged event
                // map jpa entity to swagger dto
                ServiceProblem serviceProblem = ServiceProblemMapper.INSTANCE
                        .serviceProblemEntityToServiceProblem(serviceProblemEntity);
                
                // add absolute path href for service problem
                String idStr = serviceProblem.getId();
                String requestPath = uriInfo.getAbsolutePath().toASCIIString();
                String href = requestPath.substring(0,requestPath.indexOf("/problemAcknowledgement"))+"/serviceProblem"+ "/" + idStr;

                serviceProblem.setHref(href);

                // generate acknowledged response
                ServiceProblemRef ackProblemItem = new ServiceProblemRef();
                ackProblemItem.setHref(href);
                ackProblemItem.setId(idStr);
                problemAcknowledgementResponse.addAckProblemItem(ackProblemItem);

                // service problem ack event
                ServiceProblemStateChangeNotification notification = new ServiceProblemStateChangeNotification();
                ServiceProblemStateChangeEvent event = new ServiceProblemStateChangeEvent();
                event.setServiceProblem(serviceProblem);
                // TODO eventRepository.createEvent save changed event

                notification.setEvent(event);
                notificationDispatcher.sendNotification(notification);

            }

            return Response.status(Response.Status.OK).entity(problemAcknowledgementResponse).build();

        } catch (

        Exception ex) {
            LOG.error("POST /problemAcknowledgement  createProblemAcknowledgement ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /problemAcknowledgement  createProblemAcknowledgement " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

}
