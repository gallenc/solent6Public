/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.solent.com504.project.impl.jms.MessageReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import org.solent.com504.project.impl.jms.MessageSender;
import org.solent.com504.project.impl.jms.config.AppConfig;

/**
 *
 * @author joao-
 */
public class MessagingTest {

    final static Logger LOG = LogManager.getLogger(MessagingTest.class);

    @Test
    public void SendMessageTest() {
        LOG.debug("start of SendMessagingTest");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        assertNotNull(context);
        MessageSender messageSender = context.getBean(MessageSender.class);
        assertNotNull(messageSender);
        messageSender.sendMessage("tola");
        LOG.debug("Message has been sent successfully.");

        ((AbstractApplicationContext) context).close();

    }

    @Test
    public void RecieveMessageTest() {
        LOG.debug("start of ReceiveMessagingTest");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MessageReceiver messageReceiver = (MessageReceiver) context.getBean("messageReceiver");
        assertNotNull(messageReceiver);
        JSONObject jsonMessage = messageReceiver.receiveMessage();        
        assertNotNull(jsonMessage);
        LOG.debug("Message Revieved = " + jsonMessage.getString("name"));


        ((AbstractApplicationContext) context).close();
    }

}
