/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kgill.devopsinttest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.*;

/**
 *
 * @author 4GILLK91 <4GILLK91@solent.ac.uk>
 */
public class JMSClient {

    static ActiveMQConnectionFactory connectionFactory;

    public static void main(String[] args) {
        // Create factory for getting connections to a local ActiveMQ broker
        // using the default port
        connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        
        // Header
        System.out.println("\n"
                + "### Simple JMS Client ###\n"
                + "\n"
                + "Press return to enter your input.\n"
                + "Type '>read' to get the next message on the queue\n"
                + "Type '>q' to exit");
        
        // Main loop
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in, "UTF-8"))) {
            while (true) {
                // Running
                System.out.println("Enter a message or command:");
                // Process user input
                String userInput = input.readLine();
                if (userInput.equals(">q")) {
                    // Quit
                    break;
                } else if (userInput.equals(">read")) {
                    // Read next message from queue "Test"
                    System.out.println(readMessage("Test"));
                } else {
                    // Send input text in a message to the queue "Test"
                    sendMessage("Test", userInput);
                }
            }
            System.out.println("Closing...");
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public static String sendMessage(String queueName, String userMessage) {
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
            TextMessage msg = session.createTextMessage();
            // Call Create Json method and sets it as the message.
            msg.setText(createObject().toString());
            producer.send(msg);

            System.out.println("Message sent: '" + userMessage + "'");
            
            producer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
            return "An error occurred.";
        }
        return "SUCCESS";
    }
    
    public static String readMessage(String queueName) {
        
        String message;
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            // Receive message from queue
            // Timeout after 1 second (1000ms)
            TextMessage textMessage = (TextMessage) consumer.receive(1000L);
            // Recreating the Json Object from the recieved message.
            JSONObject myJson = new JSONObject(textMessage.getText());
            if (textMessage == null) {
                message = "No message received.";
            } else {
                String text = textMessage.getText();
                // Getting JSON data through key.
                System.out.println(myJson.getString("name"));
                message = "Message received: " + text;
            }

            consumer.close();
            session.close();
            connection.close();
            
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
            return "An error occurred.";
        }
        return message;
    }
    
    // Function for default Json Object
    private static JSONObject createObject()
    {
        JSONObject myJson = new JSONObject("{ 'number_list': [ 1.9, 2.9, 3.4, 3.5 ], 'extra_data': {}, 'name': 'Carlos', 'last_name': 'Carlos', 'bank_account_balance': 20.2, 'age': 21, 'is_developer': true }");
        return myJson;
    }
    
}
