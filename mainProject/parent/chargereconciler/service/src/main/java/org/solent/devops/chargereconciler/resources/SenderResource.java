package org.solent.devops.chargereconciler.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class SenderResource {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SenderResource.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String message) {
        LOGGER.info("sending message='{}'", message);
        jmsTemplate.convertAndSend("motorway_traffic.q", message);
    }
}
