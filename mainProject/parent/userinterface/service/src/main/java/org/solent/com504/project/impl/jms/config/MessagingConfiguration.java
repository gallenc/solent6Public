/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.jms.config;

/**
 *
 * @author joao-
 */
import java.util.Arrays;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.solent.com504.project.impl.jms.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;



@Configuration
public class MessagingConfiguration
{

	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
	private static final String MESSAGE_QUEUE = "message_queue";

	@Autowired
	MessageReceiver messageReceiver;

	@Bean
	public ConnectionFactory connectionFactory()
	{
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		connectionFactory.setTrustedPackages(Arrays.asList("com.ram"));
		return connectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate()
	{
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(MESSAGE_QUEUE);
		return template;
	}

	@Bean
	MessageConverter converter()
	{
		return new SimpleMessageConverter();
	}

}