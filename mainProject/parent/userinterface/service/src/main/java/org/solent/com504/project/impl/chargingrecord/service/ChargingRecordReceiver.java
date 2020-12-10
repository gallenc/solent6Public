/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.chargingrecord.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.devops.message.jms.SimpleJmsListener;
import org.solent.devops.message.jms.StringMessageHandler;

/**
 *
 * @author cgallen
 */
public class ChargingRecordReceiver implements StringMessageHandler {

    final static Logger LOG = LogManager.getLogger(ChargingRecordReceiver.class);

    @Override
    public void onMessage(String msg) {
        LOG.debug("chargingRecordReceiver received message: " + msg);

    }

}
