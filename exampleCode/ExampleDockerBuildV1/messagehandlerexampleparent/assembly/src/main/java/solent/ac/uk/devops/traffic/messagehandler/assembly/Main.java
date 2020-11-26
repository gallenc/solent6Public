/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.devops.traffic.messagehandler.assembly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import solent.ac.uk.devops.traffic.messageexample.Controller;

/**
 * This main class starts and stops an application context. 
 * Note System.out.println and LOG used for output to ensure something is printed
 * THe shutdown may need more work.
 * @author cgallen
 */
public class Main {

    final static Logger LOG = LogManager.getLogger(Main.class);

    private ClassPathXmlApplicationContext context;
    private static Main main_m;

    public static void main(String[] args) {
        System.out.println("INFO: System.out.println - main method starting in " + Main.class);
        LOG.info("main method starting in " + Main.class);
        
        String msg=" arguments passed to main: ("+args.length+") ";
        for(int i=0; i<args.length; i++){
            msg=msg+args[i]+" ";
        }
        System.out.println("INFO: System.out.println - "+msg);
        LOG.info(msg);

        main_m = new Main();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("main shutdown hook quitting application");
                LOG.info("main shutdown hook quitting application");
                main_m.stop();
            }
        });

        main_m.start(args);

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

            Controller controller = context.getBean("controller", Controller.class);
            controller.start();

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
