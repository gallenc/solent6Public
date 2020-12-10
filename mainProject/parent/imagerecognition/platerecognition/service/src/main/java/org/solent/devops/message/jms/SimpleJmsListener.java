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
import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;

@Component
public class SimpleJmsListener implements MessageListener {

    final static Logger LOG = LogManager.getLogger(SimpleJmsListener.class);

    @Autowired
    SimpleJmsSender sender;

    String destination;
    String errorQueue;

    @Override
    public void onMessage(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
                
                if (text == null || text.isEmpty()) {
                    LOG.warn(this.toString() +
                            " received a JMS message with no text content.");
                } else {
                    LOG.info(this.toString() + " received a JMS message: '" + text.substring(0, Math.min(text.length(), 30)) + "'");
                }
                
                LOG.debug(destination);
                
                if (!destination.equals("None")) {
                    LOG.debug(this.toString() + " processing and forwarding to: '" + destination + "'");
                    ObjectMapper objectMapper = new ObjectMapper();                    
                    JSONMessage jsonMessage = objectMapper.readValue(text, JSONMessage.class);
                    if(jsonMessage.getUuid().isEmpty() || jsonMessage.getCameraId() == 0 || jsonMessage.getTimestamp() == null || jsonMessage.getPhoto().isEmpty()) {
                        throw new Exception("Missing values in received JSON"); 
                    }
                    
                    LOG.debug("Analysing with Intelligence");
                    //Intelligence intelligence = new Intelligence();
                    //CarSnapshot  carSnapshot = new CarSnapshot(jsonMessage.imageFromString());
                    //String numberplate = intelligence.recognize(carSnapshot);
                    //if(numberplate.isEmpty()){
                    //    throw new Exception("Numberplate cannot be identified");
                    //}
                    
                    // Hack for demo
                    String numberplate = "PP587A0";
                    
                    jsonMessage.setPhoto("");
                    jsonMessage.setNumberplate(numberplate);
                    String outputMessage = jsonMessage.toJson();
                    sender.send(destination, outputMessage);
                }

            } catch (final JMSException e) {
                LOG.error(this.toString() + " had a problem receiving a JMS message", e);
            } catch (final Exception e) {
                if (errorQueue != null) {
                    sender.send(errorQueue, text);
                }
                LOG.error(this.toString() + " had a problem", e);
            }
        }
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getErrorQueue() {
        return errorQueue;
    }

    public void setErrorQueue(String errorQueue) {
        this.errorQueue = errorQueue;
    }
}
