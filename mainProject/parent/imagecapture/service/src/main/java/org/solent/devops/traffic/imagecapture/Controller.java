/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.imagecapture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Map<Integer, Sender> senders;

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

    //Takes folder location as param and returns list of files
    private static File[] getResourceFolderFiles (String folder) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

    private void start(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext("appconfig-service.xml");

            context.registerShutdownHook();

            LOG.info("service bootstrap successful.");

            int maxCameraId = 10;

            senders = new HashMap<>();

            //iterates through list of files in resource and prints them
            for (File f : getResourceFolderFiles("main/resources/images")) {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String imageBinary = CameraMessage.convertImage(f);  //Sending wrong file type through
                // need to create a timestamp and cameraId
                CameraMessage message = new CameraMessage(uuid, timestamp, cameraId, imageBinary);    //need cameraID, timestamp and id
                message.toJson();
                //Need to sort the topic,qos,broker and clientId
                Sender sender = new Sender(topic, qos, broker, clientId);
                sender.sendImage(message);
            }
            //for (int i = 0; i < maxCameraId; i++) {
                // senders.push(i, new Sender("camera", 2, "tcp://localhost:1883", i));
                //Create the cameras
            //}

        } catch (Exception e) {
            LOG.error("service bootstrap failure.", e);
            if (context != null) {
                context.close();
            }
            throw new IllegalStateException("Cannot Start Application: ",e);
        }
    }

}
