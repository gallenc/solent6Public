package org.solent.devops.chargereconciler.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.devops.chargereconciler.models.Bill;
import org.solent.devops.chargereconciler.models.Charge;
import org.solent.devops.chargereconciler.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * <H1>ReceiverResource</H1>
 * <p>
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

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private SenderResource sender;

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


    @JmsListener(destination = "${receiverDestination}")
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);
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
            incomingNumberplate = JsonMessage.get("numberplate").asText();
        }

        if (!vehicleRepository.existsByNumberplate(incomingNumberplate)) {
            // Create entry vehicle and save to repository.
            Vehicle entryVehicle = null;
            try {
                entryVehicle = new ObjectMapper().readValue(message, Vehicle.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.info("saving message...'{}'", entryVehicle.toJsonString());
            vehicleRepository.save(entryVehicle);
        } else {
            // Check junctions travelled and Create charging record if chargeable
            Vehicle exitVehicle = null;
            try {
                exitVehicle = new ObjectMapper().readValue(message, Vehicle.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            int junctionsTravelled = getJunctionsTravelled(exitVehicle);
            if (junctionsTravelled >= 2) {
                Bill bill = createBill(exitVehicle, junctionsTravelled);
                String billString = bill.toJsonString();
                sender.send(billString);
                LOGGER.info("sending message...'{}'", billString);
            } else {
                // no charge?
            }
            //remove vehicle as it leaves
            vehicleRepository.deleteByNumberplate(exitVehicle.getNumberplate());
        }
        latch.countDown();
    }

    public int getJunctionsTravelled(Vehicle exitVehicle) {
        int entryJuntion = Integer.parseInt(vehicleRepository.findByNumberplate(exitVehicle.getNumberplate()).getCameraId());
        int exitJunction = Integer.parseInt(exitVehicle.getCameraId());
        return exitJunction - entryJuntion;
    }

    public LocalTime roundTimeDown(Vehicle entryVehicle) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(entryVehicle.getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newdate = new SimpleDateFormat("HH:mm:ss").format(date);
        LocalTime time = LocalTime.parse(newdate).truncatedTo(ChronoUnit.HOURS);
        return time;
    }

    public Bill createBill(Vehicle exitVehicle, int junctionsTravelled) {
        Vehicle entryVehicle = vehicleRepository.findByNumberplate(exitVehicle.getNumberplate());
        LocalTime time = roundTimeDown(entryVehicle);
        Bill bill = new Bill(entryVehicle, exitVehicle);
        Charge charge = chargeRepository.findByTime(time);
        double rate = charge.getRate();
        double total_charge = rate * junctionsTravelled;
        bill.setRate(rate);
        bill.setCharge(total_charge);
        return bill;
    }

}


