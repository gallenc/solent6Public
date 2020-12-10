package org.solent.devops.message.jms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class SimpleJmsSender {

    final static Logger LOG = LogManager.getLogger(SimpleJmsSender.class);

    private final JmsTemplate jmsTemplate;
    
    private String queueName = "Queue.Name";

    @Autowired
    public SimpleJmsSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        try {
            InputStream stream = getClass().getResourceAsStream("/testMessage.txt");
            byte[] encoded = StreamUtils.copyToByteArray(stream);
            String json = new String(encoded, Charset.forName("UTF-8"));
            
            this.send("p1ReceiveImages", json);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SimpleJmsSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(String queue, final String message) {
        LOG.debug(this.toString() + " sending to queue: '" + queue + "', '" + message + "'");
        jmsTemplate.convertAndSend(queue , message);
    }
}
