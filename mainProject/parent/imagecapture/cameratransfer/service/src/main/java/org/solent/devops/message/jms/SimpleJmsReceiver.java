package org.solent.devops.message.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SimpleJmsReceiver implements MessageListener {

    final static Logger LOG = LogManager.getLogger(SimpleJmsReceiver.class);

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
                    LOG.warn(this.toString() + " received a JMS message with no text content.");
                } else {
                    LOG.info(this.toString() + " received a JMS message: '" + text + "'");
                }

                if (!destination.equals("None")) {
                    LOG.info(this.toString() + " processing and forwarding to: '" + destination + "'");
                    ObjectMapper objectMapper = new ObjectMapper();
                    JSONMessage jsonMessage = objectMapper.readValue(text, JSONMessage.class);
                    if(jsonMessage.getUuid().isEmpty() || jsonMessage.getCameraId() == 0 || jsonMessage.getTimestamp() == null || jsonMessage.getPhoto().isEmpty()) {
                        throw new Exception("Missing values in received JSON");
                    }
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
