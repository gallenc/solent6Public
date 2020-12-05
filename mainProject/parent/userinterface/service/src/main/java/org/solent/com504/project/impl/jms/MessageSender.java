/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component
public class MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(final String message) {

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage msg = session.createTextMessage();                
                msg.setText(createObject(message).toString());
                return msg;
            }
        });
    }
    
    private static JSONObject createObject(String name)
    {
        JSONObject myJson = new JSONObject("{ 'number_list': [ 1.9, 2.9, 3.4, 3.5 ], 'extra_data': {}, 'name': '" + name + "', 'last_name': '" + name + "', 'bank_account_balance': 20.2, 'age': 21, 'is_developer': true }");
        return myJson;
    }
    
//    
//    final static Logger LOG = LogManager.getLogger(MessageSender.class);
//
//    @Autowired
//    JmsTemplate jmsTemplate;
//    
//    private String queueName = "Queue.Name";
//
//    @Autowired
//    public MessageSender(final JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    public void send(final String message) {
//        LOG.debug("jms sending to queue :"+queueName+" "+message);
//        String newMessage = "{ 'number_list': [ 1.9, 2.9, 3.4, 3.5 ], 'extra_data': {}, 'name': '" + message + "', 'last_name': '" + message + "', 'bank_account_balance': 20.2, 'age': 21, 'is_developer': true }";
//        jmsTemplate.convertAndSend( queueName , newMessage);
//    }
    
    
}
