package org.solent.devops.message.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.solent.devops.message.jms.JSONMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SimpleJmsListener implements MessageListener {

    final static Logger LOG = LogManager.getLogger(SimpleJmsListener.class);

    public SimpleJmsListener() {
        super();
        LOG.info(this.toString() + " listening");
       
    }
    
    @Override
    public void onMessage(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
                
                System.out.println("Hello from SimpleJmsListener.");
                
                if (text == null || text.isEmpty()) {
                    LOG.warn(this.toString() +
                            " received a JMS message with no text content.");
                } else {
                    LOG.info(this.toString() + " received a JMS message: '" + text + "'");
                }
                
                
                ObjectMapper objectMapper = new ObjectMapper();
                JSONMessage jsonMessage = objectMapper.readValue(text, JSONMessage.class);

                if ("PP587A0".equals(jsonMessage.getNumberplate())) {
                    LOG.info("Test 1 - Single correct message: Expected response " + jsonMessage.getNumberplate() + " received.");
                } else {
                    LOG.error("Test 1 - Single correct message: Expected PP587A0 \nReceived " + jsonMessage.getNumberplate());
                }

                if (jsonMessage.getPhoto().isEmpty()) {
                    LOG.info("Test 1 - Single correct message: Image data removed from JSON sucessfully.");
                } else {
                    LOG.error("Test 1 - Single correct message: Image data was not removed from output JSON. :(");
                }
                

            } catch (final JMSException e) {
                LOG.error(this.toString() + " had a problem receiving a JMS message", e);
            } catch (final Exception e) {
                LOG.error(this.toString() + " had a problem", e);
            }
        }
    }
}
