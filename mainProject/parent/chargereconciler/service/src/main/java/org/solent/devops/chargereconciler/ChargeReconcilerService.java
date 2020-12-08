package org.solent.devops.chargereconciler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.devops.chargereconciler.resources.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@EnableJpaRepositories("org.solent.devops.chargereconciler")
@ComponentScan("org.solent.devops.chargereconciler")
public class ChargeReconcilerService {

    private static final Logger log = LoggerFactory.getLogger(ChargeReconcilerService.class);

    public static void main(String[] args) {
        SpringApplication.run(ChargeReconcilerService.class, args);
    }

    @Bean
    public CommandLineRunner persistData(VehicleRepository vehicleRepository) {
        return (args) -> {

        };
    }
}
