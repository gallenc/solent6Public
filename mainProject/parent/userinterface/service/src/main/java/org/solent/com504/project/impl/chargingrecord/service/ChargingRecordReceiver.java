/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.chargingrecord.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PreDestroy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.solent.com504.project.model.service.ServiceFacade;
import org.solent.devops.message.jms.SimpleJmsListener;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.solent.devops.message.jms.StringMessageHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cgallen
 */
@Component
public class ChargingRecordReceiver implements StringMessageHandler, InitializingBean {

    final static Logger LOG = LogManager.getLogger(ChargingRecordReceiver.class);

    private AtomicLong charingRecordsCount = new AtomicLong();

    @Autowired
    @Qualifier("chargingRecordService")
    private ChargingRecordService chargingRecordService = null;

    @Autowired
    private SimpleJmsListener simpleJmsListener = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.debug("registering with message listener: ");
        simpleJmsListener.addMessageHandler(this);
    }

    @PreDestroy
    public void destroy() {
        LOG.debug("un registering with message listener: ");
        simpleJmsListener.removeMessageHandler(this);
    }

    public Long getCharingRecordsCount() {
        return charingRecordsCount.get();
    }
    
    public void resetCharingRecordsCount() {
        charingRecordsCount.set(0);
    }

    // do in transaction so that database is committed
    @Override
    @Transactional
    public void onMessage(String msg) {
        LOG.debug("chargingRecordReceiver received message: " + msg);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JaxbAnnotationModule());
        try {
            ChargingRecord receivedChargingRecord;
            receivedChargingRecord = objectMapper.readValue(msg, ChargingRecord.class);
            chargingRecordService.save(receivedChargingRecord);
            long count = charingRecordsCount.getAndIncrement();
            LOG.debug("saved charging record " + count + " : " + receivedChargingRecord.toString());
        } catch (IOException ex) {
            LOG.error("problem reading json value: " + msg);
        }

    }

}
