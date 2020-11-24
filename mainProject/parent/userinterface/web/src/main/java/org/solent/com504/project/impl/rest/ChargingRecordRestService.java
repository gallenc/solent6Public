/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.rest;

/**
 *
 * @author gallenc
 */
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.solent.com504.project.model.dto.ReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * To make the ReST interface easier to program. All of the replies are contained in ReplyMessage classes but only the fields indicated are populated with each
 * reply. All replies will contain a code and a debug message. Possible replies are: List<String> replyMessage.getStringList() AnimalList
 * replyMessage.getAnimalList() int replyMessage.getCode() replyMessage.getDebugMessage(); * @author cgallen
 */
@Component // component allows resource to be picked up
@Path("/traffic")
public class ChargingRecordRestService {

    public static final String WEB_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // SETS UP LOGGING 
    // note that log name will be org.solent.com504.project.impl.rest.RestService
    final static Logger LOG = LogManager.getLogger(ChargingRecordRestService.class);

    // This chargingRecordService object is injected by Spring
    @Autowired(required = true)
    @Qualifier("chargingRecordService")
    ChargingRecordService chargingRecordService = null;

    // Swagger annotations
    @Operation(summary = "Create Charging Record",
            description = "creates a new charging record in database",
            responses = {
                @ApiResponse(description = "charging record in chargingRecordList or empty list if not found",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        )),
                @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })
    @POST
    @Path("/ChargingRecord")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addChargingRecord(ChargingRecord chargingRecord) {
        try {
            if (chargingRecordService == null) {
                throw new IllegalStateException("chargingRecordService has not been initialised");
            }
            if (chargingRecord == null) {
                throw new IllegalArgumentException("ChargingRecord cannot be null");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            chargingRecord = chargingRecordService.save(chargingRecord);
            LOG.debug("post /chargingRecord persisted chargingRecord=" + chargingRecord);

            List<ChargingRecord> crList = new ArrayList<ChargingRecord>();
            crList.add(chargingRecord);

            replyMessage.setChargingRecordList(crList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("post /chargingRecord ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("post /chargingRecord " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    // Swagger annotations
    @Operation(summary = "Get a charging Record by UUID",
            description = "Retrieves a charging record by uuid",
            responses = {
                @ApiResponse(description = "single charging record in chargingRecordList of reply or empty list if not found",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        )),
                @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })
    @GET
    @Path("/ChargingRecord/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getChargingRecord(@PathParam("uuid") String uuid) {
        try {

            LOG.debug("get /chargingRecord/uuid uuid=" + uuid);

            if (chargingRecordService == null) {
                throw new IllegalStateException("chargingRecordService has not been initialised");
            }
            if (uuid != null) {
                throw new IllegalArgumentException("ChargingRecord cannot be null");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            ChargingRecord chargingRecord = chargingRecordService.findByUuid(uuid);

            List<ChargingRecord> crList = new ArrayList<ChargingRecord>();
            crList.add(chargingRecord);

            replyMessage.setChargingRecordList(crList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("get /chargingRecord ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("get /chargingRecord " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    // Swagger annotations
    @Operation(summary = "Get Charging Record List",
            description = "retrieves list of charging records from database",
            responses = {
                @ApiResponse(description = "charging record in chargingRecordList or empty list if not found",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        )),
                @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })
    @GET
    @Path("/ChargingRecordList")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addChargingRecordList(@QueryParam("numberPlate") String numberPlate,
            @QueryParam("page") String page,
            @QueryParam("size") String size,
            @QueryParam("entryDate") String entryDate,
            @QueryParam("exitDate") String exitDate
    ) {
        try {

            SimpleDateFormat df = new SimpleDateFormat(WEB_DATE_FORMAT);

            LOG.debug("get /chargingRecordList numberPlate=" + numberPlate
                    + " page=" + page
                    + " size=" + size);

            if (chargingRecordService == null) {
                throw new IllegalStateException("chargingRecordService has not been initialised");
            }

            ReplyMessage replyMessage = new ReplyMessage();
            Date m_entryDate = null;
            Date m_exitDate = null;
            Integer m_page = 0;
            Integer m_size = 20;
            Long m_totalRecords = 0L;

            try {
                m_entryDate = (entryDate == null || entryDate.isEmpty() ) ? null : df.parse(entryDate);
                m_exitDate = (exitDate == null|| exitDate.isEmpty()) ? null : df.parse(exitDate);
                m_page = (page == null || page.isEmpty()) ? null : Integer.parseInt(page);
                m_size = (size == null || size.isEmpty()) ? null : Integer.parseInt(size);
            } catch (Exception ex) {
                throw new IllegalArgumentException("cannot parse query", ex);
            }

            m_totalRecords = chargingRecordService.totalRecordsByNumberPlate(numberPlate, m_entryDate, m_exitDate);

            replyMessage.setTotalRecords(m_totalRecords);

            replyMessage.setPage(m_page);
            replyMessage.setSize(m_size);

            List<ChargingRecord> chargingRecordList = chargingRecordService.findByNumberPlate(numberPlate, m_entryDate, m_exitDate, m_page, m_size);

            replyMessage.setChargingRecordList(chargingRecordList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("get /chargingRecord ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("get /chargingRecord " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

}
