package org.solent.com504.project.impl.jms;

import javax.jms.Message;
import javax.jms.TextMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component
public class MessageReceiver {

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    MessageConverter messageConverter;

    public JSONObject receiveMessage() {
        try {
            TextMessage message = (TextMessage) jmsTemplate.receive();
            JSONObject messageJson = new JSONObject(message.getText());
            return messageJson;

        } catch (Exception exe) {
            exe.printStackTrace();
        }

        return null;
    }
}
