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
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.service.ServiceFacade;
import org.solent.devops.message.jms.SimpleJmsListener;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.solent.devops.message.jms.StringMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MessageSenderReceiverTest {

    final static Logger LOG = LogManager.getLogger(MessageSenderReceiverTest.class);

    @Autowired
    ServiceFacade serviceFacade = null;

    @Autowired
    SimpleJmsSender simpleJmsSender = null;

    @Autowired
    SimpleJmsListener simpleJmsListener = null;

    // can be accessed concurrently by seperate threads
    List<ChargingRecord> chargingRecords = new CopyOnWriteArrayList<ChargingRecord>();

    //  Thread messageReceiverTread;
    StringMessageHandler messageHandler;

    @Before
    public void before() {
        assertNotNull(serviceFacade);
        assertNotNull(simpleJmsListener);
        assertNotNull(simpleJmsSender);

        chargingRecords.clear();

        messageHandler = new StringMessageHandler() {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JaxbAnnotationModule());

            @Override
            public void onMessage(String msg) {
                LOG.debug("received message " + msg);
                try {
                    ChargingRecord receivedChargingRecord;
                    receivedChargingRecord = objectMapper.readValue(msg, ChargingRecord.class);
                    chargingRecords.add(receivedChargingRecord);
                    LOG.debug("saved charging record: " + receivedChargingRecord.toString());
                } catch (IOException ex) {
                    LOG.error("problem reading json value: " + msg);
                }

            }

        };

        // remember to remove listener before shutdown
        simpleJmsListener.addMessageHandler(messageHandler);

//        messageReceiverTread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (!Thread.interrupted()) {
//                        Thread.sleep(500);
//                    }
//                } catch (InterruptedException e) {
//                    LOG.debug("messageReceiverThread interrupted. Shutting down");
//                }
//
//            }
//
//        }
//        );
//        messageReceiverTread.start();
    }

    @After
    public void after() {
        simpleJmsListener.removeMessageHandler(messageHandler);
        simpleJmsListener.clearAllMessageHandlers();
        chargingRecords.clear();

        //    if (messageReceiverTread != null) {
        //        messageReceiverTread.interrupt();
        //    }
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

        ObjectMapper objectMapper = new ObjectMapper();
        // writes prity output see https://mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JaxbAnnotationModule());

        Date entryDate = new Date();
        int TEST_MESSAGE_NUMBER = 100;
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

        // short delay to receive all messages
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        assertEquals(TEST_MESSAGE_NUMBER, chargingRecords.size());

        LOG.debug("end MessageReceiverTest testMessages");
    }

    // WHAT OTHER TESTS DO YOU NEED FOR THE SERVICE?
}
