package org.solent.devops.chargereconciler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.devops.chargereconciler.models.Charge;
import org.solent.devops.chargereconciler.models.ChargeScheme;
import org.solent.devops.chargereconciler.models.Vehicle;
import org.solent.devops.chargereconciler.resources.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJms
@EnableJpaRepositories("org.solent.devops.chargereconciler")
@ComponentScan("org.solent.devops.chargereconciler")
public class ChargeReconcilerService {

    @Autowired
    private SenderResource sender;

    @Autowired
    private ReceiverResource receiver;

    @Autowired
    private ChargeSchemeRepository chargeSchemeRepository;

    @Autowired
    private ChargeRepository chargeRepository;

    private static final Logger log = LoggerFactory.getLogger(ChargeReconcilerService.class);

    public static void main(String[] args) {
        SpringApplication.run(ChargeReconcilerService.class, args);
    }

    public void loadFile() {
        //todo add check for file
        // process csv if exists
        String COMMA_DELIMITER = "\t";
        ChargeScheme chargeScheme = new ChargeScheme();
        Set<Charge> charges = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("chargereconciler/service/src/main/java/org/solent/devops/chargereconciler/schedule.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime time = LocalTime.parse(values[0], formatter);
                double rate = Double.parseDouble(values[1]);
                Charge charge = new Charge(chargeScheme);
                charge.setTime(time);
                charge.setRate(rate);
                charges.add(charge);
            }
            //set charges
            chargeScheme.setChargeScheme(charges);
            chargeSchemeRepository.save(chargeScheme);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Bean
    public CommandLineRunner persistData(VehicleRepository vehicleRepository) {
        return (args) -> {
            loadFile();

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
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);

            testVehicle = new Vehicle(
                    "123e4567-e89b-12d3-a456-556642440000",
                    "5",
                    "2020-11-05T18:24:30.000+00:00",
                    "PP587AO",
                    "");
            testVehicleJson = testVehicle.toJsonString();
            mapper = new ObjectMapper();
            jsonVehicle = mapper.readValue(testVehicleJson, Vehicle.class);
            sender.send(jsonVehicle.toJsonString());
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        };
    }*/
}
