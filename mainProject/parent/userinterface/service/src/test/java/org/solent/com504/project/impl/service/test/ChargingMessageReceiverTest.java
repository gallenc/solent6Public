/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.service.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.chargingrecord.service.ChargingRecordReceiver;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.solent.com504.project.model.service.ServiceFacade;
import org.solent.devops.message.jms.SimpleJmsListener;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.solent.devops.message.jms.StringMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author gallenc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service-test.xml"})
public class ChargingMessageReceiverTest {

    private static int TEST_MESSAGE_NUMBER = 100;

    final static Logger LOG = LogManager.getLogger(ChargingMessageReceiverTest.class);

    @Autowired
    ServiceFacade serviceFacade = null;

    @Autowired
    SimpleJmsSender simpleJmsSender = null;

    @Autowired
    ChargingRecordReceiver chargingRecordReceiver = null;

    @Autowired
    ChargingRecordService chargingRecordService = null;

    //  Thread messageReceiverTread;
    StringMessageHandler messageHandler;

    @Before
    public void before() {
        assertNotNull(serviceFacade);
        assertNotNull(simpleJmsSender);
        assertNotNull(chargingRecordReceiver);
        assertNotNull(chargingRecordService);

        // remove all charging records before test
        chargingRecordService.deleteAll();
    }

    @After
    public void after() {
        // remove all charging records before test
        chargingRecordService.deleteAll();
    }

    @Test
    public void testFactory() {
        LOG.debug("start MessageReceiverTest testFactory");
        assertNotNull(serviceFacade);
        LOG.debug("end MessageReceiverTest testFactory");
    }

    @Test
    public void testMessages() {
        LOG.debug("start MessageReceiverTest testMessages");
        assertNotNull(simpleJmsSender);

        // reset count
        chargingRecordReceiver.resetCharingRecordsCount();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JaxbAnnotationModule());

        Date entryDate = new Date();

        for (int i = 0; i < TEST_MESSAGE_NUMBER; i++) {

            ChargingRecord chargingRecord = new ChargingRecord();
            chargingRecord.setUuid(UUID.randomUUID().toString());
            chargingRecord.setCharge(10.0);
            chargingRecord.setChargeRate(1.0);
            chargingRecord.setEntryDate(entryDate);
            chargingRecord.setEntryLocation("Southampton");
            chargingRecord.setEntryPhotoId(UUID.randomUUID().toString());
            Date exitDate = new Date(entryDate.getTime() + (60 * 1000)); // + 1 hour
            chargingRecord.setExitDate(exitDate);
            chargingRecord.setExitLocation("London");
            chargingRecord.setExitPhotoId(UUID.randomUUID().toString());
            chargingRecord.setNumberPlate("HAZ6204");

            StringWriter sw1 = new StringWriter();
            try {
                objectMapper.writeValue(sw1, chargingRecord);
            } catch (IOException ex) {
                throw new IllegalStateException("problem mapping charging record:", ex);
            }

            String message = sw1.toString();
            LOG.debug("sending Message: " + message);
            simpleJmsSender.send(message);

            // increment entry date 
            entryDate = new Date(entryDate.getTime() + (24 * 60 * 1000)); // + 24 hour
        }

        LOG.debug("finished sending messages: " + TEST_MESSAGE_NUMBER);

        // short delay to receive all messages
        boolean stop = false;
        
        while (!stop) {
            if (chargingRecordReceiver.getCharingRecordsCount() >= TEST_MESSAGE_NUMBER) {
                stop = true;
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        List<ChargingRecord> chargingRecordList = chargingRecordService.findAll();

        LOG.debug("end MessageReceiverTest sent " + TEST_MESSAGE_NUMBER
                + " messages. Added charging records to database: " + chargingRecordList.size());
        assertEquals(TEST_MESSAGE_NUMBER, chargingRecordList.size());

        LOG.debug("end MessageReceiverTest testMessages");
    }

    // WHAT OTHER TESTS DO YOU NEED FOR THE SERVICE?
}
