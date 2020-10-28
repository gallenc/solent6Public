package devops.p3.reconcilechargesservice.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import devops.p3.reconcilechargesservice.models.Vehicle;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

public class ReceiverResource {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ReceiverResource.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String num_plate;

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "motorway_traffic.q")
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);
        try {
            Vehicle NewVehicle = new ObjectMapper().readValue(message, Vehicle.class);

            // check if entry or exit - read data from file for now?

            // calculate charge - read charge rate from file for now?

            // output full vehicle

        } catch (JsonProcessingException e) {
            LOGGER.info(e.toString());
        }

        latch.countDown();
    }

    public String getNum_plate() {
        return num_plate;
    }
}
