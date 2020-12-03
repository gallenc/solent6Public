package org.solent.devops.chargereconciler.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.devops.chargereconciler.ChargeReconcilerService;
import org.solent.devops.chargereconciler.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableJms
public class ChargeReconcilerServiceTests {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ChargeReconcilerService.class);

    @Autowired
    private SenderResource sender;

    @Autowired
    private ReceiverResource receiver;

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger log = LoggerFactory.getLogger(ChargeReconcilerService.class);

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(sender).isNotNull();
        assertThat(receiver).isNotNull();
        assertThat(jmsTemplate).isNotNull();
    }

    @Test
    public void testReceive() throws Exception {

        Vehicle testVehicle = new Vehicle(
                "123e4567-e89b-12d3-a456-556642440000",
                "1",
                "2020-11-05T17:24:30.000+00:00",
                "PP587AO",
                "");
        String testVehicleJson = testVehicle.toJsonString();
        ObjectMapper mapper = new ObjectMapper();
        Vehicle jsonVehicle = mapper.readValue(testVehicleJson, Vehicle.class);
        sender.send(jsonVehicle.toJsonString());


        //todo fix stupid assertion import
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

        int actual = (int) receiver.getLatch().getCount();
        int expected = 0;
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }

}
