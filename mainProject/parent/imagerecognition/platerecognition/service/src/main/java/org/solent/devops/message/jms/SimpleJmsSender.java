package org.solent.devops.message.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleJmsSender {

    final static Logger LOG = LogManager.getLogger(SimpleJmsSender.class);

    private final JmsTemplate jmsTemplate;
    
    private String queueName = "Queue.Name";

    @Autowired
    public SimpleJmsSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String queue, final String message) {
        LOG.debug(this.toString() + " sending to queue: '" + queue + "', '" + message + "'");
        jmsTemplate.convertAndSend(queue , message);
    }
}
