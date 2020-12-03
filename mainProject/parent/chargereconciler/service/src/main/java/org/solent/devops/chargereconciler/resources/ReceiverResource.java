package org.solent.devops.chargereconciler.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.devops.chargereconciler.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <H1>ReceiverResource</H1>
 *
 * ReceiverResource is the main class to receive messages from a jms queue.
 *
 * @author Aaron Jenkins
 * @version 1.0
 * @since 25/11/2020
 */

@Service
public class ReceiverResource {

    /**
     * used for logging
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ReceiverResource.class);
    /**
     * injects repository
     */
    @Autowired
    private VehicleRepository vehicleRepository;
    /**
     * used for test purposes
     */
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * @return current latch
     */
    public CountDownLatch getLatch() {
        return latch;
    }


    @JmsListener(destination = "motorway_traffic.q")
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);

        latch.countDown();


        //todo convert to local method
        //get incoming numberplate
        String incomingNumberplate = null;
        JsonNode JsonMessage = null;
        try {
            JsonMessage = new ObjectMapper().readTree(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (JsonMessage.has("numberplate")) {
            incomingNumberplate = JsonMessage.get("numberplate").toString();
        }

/*        //todo convert to local method
        //check numberplate exists, if it doesn't add to db else create charging record.
        if (!vehicleRepository.existsByNumberplate(incomingNumberplate)) {
            //todo create EntryVehicle and add to db.
            Vehicle vehicle = null;
            try {
                vehicle = new ObjectMapper().readValue(message, Vehicle.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.info("saving message...'{}'", vehicle.toJsonString());
            vehicleRepository.save(vehicle);
        } else {
            //todo create charging record
        }*/


        // calculate charge - read charge rate from file for now?

        // output full vehicle


    }


}


