package org.solent.devops.chargereconciler.resources;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderResource {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SenderResource.class);

    @Value("${senderDestination}")
    private String destination;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String message) {
        LOGGER.info("sending message='{}'", message);

        jmsTemplate.convertAndSend(destination, message);
    }
}
