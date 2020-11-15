package devops.p3.reconcilechargesservice;

import devops.p3.reconcilechargesservice.models.Vehicle;
import devops.p3.reconcilechargesservice.resources.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ReconcileChargesServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(ReconcileChargesServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReconcileChargesServiceApplication.class, args);
	}


	// this method is just used to modify the default in memory database untill I setup production instance.
	@Bean
	public CommandLineRunner persistData(VehicleRepository vehicleRepository) {
		return (args) -> {
		};
	}
}
