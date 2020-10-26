/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.devops.traffic.messagehandler.assembly;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import solent.ac.uk.devops.traffic.messageexample.Controller;

/**
 *
 * @author cgallen
 */
public class Main {

    final static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("INFO: System.out.println - main method starting in " + Main.class);
        LOG.info("main method starting in " + Main.class);

        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("appconfig-service.xml");

            // NOTE - VERY IMPORTANT - this shuts down application context cleanly when program ends
            context.registerShutdownHook();

            Controller controller = context.getBean("controller", Controller.class);
            controller.start();

            LOG.info("service bootstrap successful.");

            Scanner userInput = new Scanner(System.in);
            while (true) {
                // loop waiting for input
                System.out.println("Waiting for comand input - any character terminates");
                String input = userInput.nextLine();
                System.out.println("input is '" + input + "'");
                if (!input.isEmpty()) {
                    break;
                }
            }
            context.close();

        } catch (Exception e) {
            LOG.error("service bootstrap failure.", e);
        } finally {
            if (context != null) {
                context.close();
            }
        }

    }

}
