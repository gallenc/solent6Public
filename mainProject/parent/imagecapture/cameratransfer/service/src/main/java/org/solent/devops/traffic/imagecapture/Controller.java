/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.imagecapture;

import com.solent.devops.traffic.csvtocamera.Camera;
import com.solent.devops.traffic.csvtocamera.CameraCsv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.devops.message.jms.JSONMessage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.*;

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
    private Properties prop;

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
            FileReader reader = new FileReader("/deployments/properties/system.properties");
            prop = new Properties();
            prop.load(reader);
            context.registerShutdownHook();

            LOG.info("service bootstrap successful.");

            senders = new HashMap<>();
            CameraCsv cameraCsv = new CameraCsv();
            for (Camera c : cameraCsv.GetCameras()) {
                URL resource = this.getClass().getClassLoader().getResource(c.getImageName());
                JSONMessage jsonMessage = new JSONMessage(c.getUuid(), c.getCamerId(), c.getDateTime(), null, convertImage(new File(resource.toURI())));
                Sender sender = new Sender(System.getProperty("mqtt.topic"), Integer.getInteger(System.getProperty("mqtt.qos")), System.getProperty("mqtt.url"), Integer.toString(jsonMessage.getCameraId()));
                sender.sendImage(jsonMessage);
            }

        } catch (Exception e) {
            LOG.error("service bootstrap failure.", e);
            if (context != null) {
                context.close();
            }
            throw new IllegalStateException("Cannot Start Application: ",e);
        }
    }

    //Convert image file to binary
    public static String convertImage(File imageFile) {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(imageFile))) {
            for (int i; (i = is.read()) != -1;) {
                String temp = "0000000" + Integer.toBinaryString(i).toUpperCase();
                if (temp.length() == 1) {
                    sb.append('0');
                }
                temp = temp.substring(temp.length() - 8);
                sb.append(temp).append(' ');
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed image binary conversion ";
    }
}
