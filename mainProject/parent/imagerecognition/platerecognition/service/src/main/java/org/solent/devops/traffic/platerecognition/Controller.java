/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.platerecognition;

import java.util.Arrays;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This Controller class starts and stops an application context. 
 * The shutdown may need more work.
 * @author cgallen
 */
@Component
public class Controller {
    final static Logger LOG = LogManager.getLogger(Controller.class);
    
    private ClassPathXmlApplicationContext context;
    private static Controller controller_m;

    public static void main(String[] args) {
        LOG.info("Main method starting in " + Controller.class);
        
        // Create Controller instance that will contain application context
        controller_m = new Controller();

        // Shutdown hook will run when program execution terminates
        // This will shut down the application context
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOG.info("Main shutdown hook quitting application");
                controller_m.stop();
            }
        });

        // Start controller - Starting "appconfig-service.xml"
        controller_m.start(args);

        // Wait for shutdown - may never complete
        try {
            LOG.debug("waiting for interrupt to shut down application");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("current thread interrupted");
            LOG.info("current thread  interrupted");
        }
        LOG.info("stopping application");
        System.out.println("stopping application");

    }

    private void stop() {
        System.out.println("Quitting application on stop");
        LOG.info("Quitting application on stop");
        if (context != null) {
            context.close();
        }
    }

    private void start(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext("appconfig-service.xml");
            // NOTE - VERY IMPORTANT - this shuts down application context cleanly when program ends
            context.registerShutdownHook();
            
            // Debugging
            System.out.println(context.getBean(ActiveMQConnectionFactory.class).getBrokerURL());
            System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
            
            // Testing of bootstrap architecture
            LOG.info("Program starting.");
            SimpleJmsSender sender = context.getBean(SimpleJmsSender.class);
            // Simulate messages from P1
            
            sender.send("p2imagerecognition", "Example Output 1");  
            sender.send("p2imagerecognition", "Example Output 2");
            sender.send("p2imagerecognition", "Example Output 3");

            LOG.info("service bootstrap successful.");

        } catch (Exception e) {
            LOG.error("service bootstrap failure.", e);
            if (context != null) {
                context.close();
            }
            throw new IllegalStateException("Cannot Start Application: ",e);
        }
    }

}
