package devops.p3.reconcilechargesservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.p3.reconcilechargesservice.models.Vehicle;
import devops.p3.reconcilechargesservice.resources.ReceiverResource;
import devops.p3.reconcilechargesservice.resources.SenderResource;
import devops.p3.reconcilechargesservice.resources.VehicleRepository;
import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import javax.persistence.EntityManager;


@SpringBootTest
class ReconcileChargesServiceApplicationTests {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(ReconcileChargesServiceApplicationTests.class);

	@Autowired
	private SenderResource sender;

	@Autowired
	private ReceiverResource receiver;

	private static final Logger log = LoggerFactory.getLogger(ReconcileChargesServiceApplication.class);

	@Test
	public void testReceive() throws Exception {

/*		//todo - setup activemq
		BrokerService broker = new BrokerService();
		// configure the broker
		broker.addConnector("http://127.0.0.1:8161");
		broker.start();*/

		Vehicle TestVehicle = new Vehicle("123", "12:00", "camera_1");
		String testVehicle = testVehicle = new ObjectMapper().writeValueAsString(TestVehicle);

		sender.send(testVehicle);

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	}

}
