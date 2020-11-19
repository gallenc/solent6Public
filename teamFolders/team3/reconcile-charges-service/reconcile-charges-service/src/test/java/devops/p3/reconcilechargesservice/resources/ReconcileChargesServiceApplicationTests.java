package devops.p3.reconcilechargesservice.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import devops.p3.reconcilechargesservice.ReconcileChargesServiceApplication;
import devops.p3.reconcilechargesservice.models.Vehicle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;


@SpringBootTest
@EnableJms
class ReconcileChargesServiceApplicationTests {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(ReconcileChargesServiceApplicationTests.class);

	@Autowired
	private SenderResource sender;

	@Autowired
	private ReceiverResource receiver;

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger log = LoggerFactory.getLogger(ReconcileChargesServiceApplication.class);

	@Test
	public void testReceive() throws Exception {

		//todo - setup embedded activemq broker

		Vehicle TestVehicle = new Vehicle(
				"123e4567-e89b-12d3-a456-556642440000",
				"1",
				"2020-11-05T17:24:30.000+00:00",
				"PP587AO",
				"");
		String testVehicleJson = TestVehicle.toJsonString();
		ObjectMapper mapper = new ObjectMapper();
		Vehicle JsonVehicle = mapper.readValue(testVehicleJson, Vehicle.class);
		sender.send(JsonVehicle.toJsonString());

		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	}

}
