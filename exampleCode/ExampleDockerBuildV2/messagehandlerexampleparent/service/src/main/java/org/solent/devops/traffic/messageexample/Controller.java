/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.messageexample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This Controller class starts and stops an application context. 
 * Note System.out.println and LOG used for output to ensure something is printed
 * THe shutdown may need more work.
 * @author cgallen
 */
@Component
public class Controller {
    final static Logger LOG = LogManager.getLogger(Controller.class);

    private ClassPathXmlApplicationContext context;
    private static Controller controller_m;

    public static void main(String[] args) {
        System.out.println("INFO: System.out.println - main method starting in " + Controller.class);
        LOG.info("main method starting in " + Controller.class);
        
        String msg=" arguments passed to main: ("+args.length+") ";
        for(int i=0; i<args.length; i++){
            msg=msg+args[i]+" ";
        }
        System.out.println("INFO: System.out.println - "+msg);
        LOG.info(msg);

        controller_m = new Controller();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("main shutdown hook quitting application");
                LOG.info("main shutdown hook quitting application");
                controller_m.stop();
            }
        });

        controller_m.start(args);

        // waits for shutdown - may never complete
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
