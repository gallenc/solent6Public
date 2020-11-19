package org.solent.devops.message.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.solent.devops.message.jms.JSONMessage;

@Component
public class SimpleJmsListener implements MessageListener {

    final static Logger LOG = LogManager.getLogger(SimpleJmsListener.class);

    @Autowired
    SimpleJmsSender sender;
    
    String destination;
    
    private String lastMessage;
    
    @Override
    public void onMessage(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                setLastMessage(text);
                LOG.info(this.toString() + " received a JMS message: " + text);
                //sender.send(destination, text);
                
                //JSONMessage json;

            } catch (final JMSException e) {
                LOG.error(this.toString() + " had a problem receiving a JMS message", e);
            }
        }
    }
    
    public String getLastMessage() {
        return lastMessage;
    }
    
    private void setLastMessage(String message) {
        this.lastMessage = message;
    }
}
