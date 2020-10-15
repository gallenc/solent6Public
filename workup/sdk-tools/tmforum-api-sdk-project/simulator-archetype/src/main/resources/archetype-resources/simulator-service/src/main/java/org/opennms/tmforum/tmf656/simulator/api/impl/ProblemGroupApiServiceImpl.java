#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.api.impl;

import ${package}.${tmfSpecPackageName}.swagger.api.*;
import ${package}.${tmfSpecPackageName}.swagger.model.*;

import ${package}.${tmfSpecPackageName}.swagger.model.Error;
import org.opennms.tmforum.tmf650.hub.impl.NotificationDispatcher;
import org.opennms.tmforum.tmf650.impl.NewJacksonFeature;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.dao.ServiceProblemRepository;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.mapper.ServiceProblemMapper;
import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
public class ProblemGroupApiServiceImpl extends ProblemGroupApiService {
    final static Logger LOG = LoggerFactory.getLogger(ProblemGroupApiServiceImpl.class);

    @Inject
    private ServiceProblemRepository serviceProblemRepository;

    @Inject
    NotificationDispatcher notificationDispatcher;

    @Override
    public Response createProblemGroup(ProblemGroupCreate problemGroupCreate, SecurityContext securityContext,
            javax.ws.rs.core.UriInfo uriInfo) throws NotFoundException {

        try {
            LOG.debug("POST /problemGroup createProblemGroup called");

            LOG.debug("problemGroupCreate:" + problemGroupCreate);

            ProblemGroup problemGroupResponse = new ProblemGroup();
            problemGroupResponse.setChildProblem(new ArrayList<ServiceProblemRef>());

            if (problemGroupCreate == null)
                throw new IllegalArgumentException("problemGroupCreate cannot be null");

            ServiceProblemRef parentProblemRef = problemGroupCreate.getParentProblem();

            if (parentProblemRef == null) {
                throw new IllegalArgumentException("parentProblemRef cannot be null");
            }

            // try to find parent
            Long parentid = null;
            String parentidStr = parentProblemRef.getId();
            try {
                parentid = Long.parseLong(parentidStr);
            } catch (Exception ex) {
                throw new IllegalArgumentException("cannot parse parentProblemRef id:" + parentProblemRef.getId());
            }
            Optional<ServiceProblemEntity> optionalParentspe = serviceProblemRepository.findById(parentid);
            if (!optionalParentspe.isPresent()) {
                throw new IllegalArgumentException(
                        "parent problem does not exist parentProblemRef id:" + parentProblemRef.getId());
            }

            // now add full reference to parentProblemRef because there may only be the id
            // provided
            // add absolute path href for service problem
            String requestPath = uriInfo.getAbsolutePath().toASCIIString();
            String href = requestPath.substring(0, requestPath.indexOf("/problemGroup")) + "/serviceProblem" + "/"
                    + parentidStr;
            parentProblemRef.setHref(href);

            problemGroupResponse.setParentProblem(parentProblemRef);

            ServiceProblemEntity parentProblemEntity = optionalParentspe.get();
            List<ServiceProblemRef> parentsUnderlyingProblems = parentProblemEntity.getUnderlyingProblem();

            // find children if present
            List<ServiceProblemRef> childProblemRefs = problemGroupCreate.getChildProblem();
            if (childProblemRefs == null) {
                throw new IllegalArgumentException("childProblem cannot be null");
            }

            List<ServiceProblemEntity> childUpdateList = new ArrayList<ServiceProblemEntity>();

            // for each child problem to update
            for (ServiceProblemRef childProblemref : childProblemRefs) {
                Long childId = null;
                try {
                    childId = Long.parseLong(childProblemref.getId());
                } catch (Exception ex) {
                    throw new IllegalArgumentException("cannot parse child problemref id:" + childProblemref.getId());
                }

                //TODO FIX THIS  work around for problems with jackson marshalling
                // see
                // https://stackoverflow.com/questions/28821715/java-lang-classcastexception-java-util-linkedhashmap-cannot-be-cast-to-com-test
                parentsUnderlyingProblems = NewJacksonFeature.getObjectMapper().convertValue(parentsUnderlyingProblems,
                        new TypeReference<List<ServiceProblemRef>>() {
                        });

                // check parent doesn't already have child in child references
                boolean childRefExists = false;

                ListIterator<ServiceProblemRef> parentsUnderlyingProblemsRefsIterator = parentsUnderlyingProblems
                        .listIterator();
                while (parentsUnderlyingProblemsRefsIterator.hasNext() && !childRefExists) {
                    ServiceProblemRef childRef = parentsUnderlyingProblemsRefsIterator.next();
                    childRefExists = childProblemref.getId().equals(childRef.getId());
                }

                // if parent does have child assume child also has parent and do nothing more
                if (!childRefExists) {
                    // else find child service problem to update
                    Optional<ServiceProblemEntity> optionalspe = serviceProblemRepository.findById(childId);
                    if (optionalspe.isPresent()) {
                        childUpdateList.add(optionalspe.get());
                    } else {
                        LOG.debug("problemGroup cannot find child service problem id:" + childId);
                    }
                }
            }

            // update all found children and send out change events
            for (ServiceProblemEntity childServiceProblemEntity : childUpdateList) {

                List<ServiceProblemRef> parentProblemRefs = childServiceProblemEntity.getParentProblem();

                // add reference to this child in parent problem
                parentProblemRefs.add(parentProblemRef);
                
                

                // add absolute path href for child service problem
                String childIdStr = Long.toString(childServiceProblemEntity.getId());
                String childhref = requestPath.substring(0, requestPath.indexOf("/problemGroup")) + "/serviceProblem"
                        + "/" + childIdStr;
                childServiceProblemEntity.setHref(childhref);

                // persist updated jpa entity
                childServiceProblemEntity = serviceProblemRepository.save(childServiceProblemEntity);

                // map jpa entity to swagger dto
                ServiceProblem childServiceProblem = ServiceProblemMapper.INSTANCE
                        .serviceProblemEntityToServiceProblem(childServiceProblemEntity);

                // update group response
                ServiceProblemRef childProblemItem = new ServiceProblemRef();
                childProblemItem.setHref(childhref);
                childProblemItem.setId(childIdStr);
                problemGroupResponse.getChildProblem().add(childProblemItem);
                
                // update parent to also reference child
                parentsUnderlyingProblems.add(childProblemItem);

                // generate updated event
                ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
                ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
                event.setServiceProblem(childServiceProblem);
                // TODO eventRepository.createEvent save changed event

                notification.setEvent(event);
                notificationDispatcher.sendNotification(notification);

            }

            // update parent and send change events
            if (!childUpdateList.isEmpty()) {
                // persist updated jpa entity
                // this nudges hibernate to ensure that parent problems are persisted
                parentProblemEntity.setUnderlyingProblem(parentsUnderlyingProblems);
                parentProblemEntity = serviceProblemRepository.save(parentProblemEntity);
                // map jpa entity to swagger dto
                ServiceProblem parentServiceProblem = ServiceProblemMapper.INSTANCE
                        .serviceProblemEntityToServiceProblem(parentProblemEntity);
                // generate parent updated event
                ServiceProblemAttributeValueChangeNotification notification = new ServiceProblemAttributeValueChangeNotification();
                ServiceProblemAttributeValueChangeEvent event = new ServiceProblemAttributeValueChangeEvent();
                event.setServiceProblem(parentServiceProblem);
                // TODO eventRepository.createEvent save changed event

                notification.setEvent(event);
                notificationDispatcher.sendNotification(notification);

            }

            return Response.status(Response.Status.OK).entity(problemGroupResponse).build();

        } catch (Exception ex) {
            LOG.error("POST /problemGroup  createProblemGroup ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "POST /problemGroup  createProblemGroup " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }
}
