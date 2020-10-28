package devops.p3.reconcilechargesservice;

import devops.p3.reconcilechargesservice.models.Vehicle;
import devops.p3.reconcilechargesservice.resources.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@Transactional
public class VehicleRepositoryTest {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ReconcileChargesServiceApplicationTests.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(entityManager).isNotNull();
        assertThat(vehicleRepository).isNotNull();
    }

   @Test
    void save_numberPlate() {
        assertThat(vehicleRepository.save(new Vehicle(
                "LA51 ABC",
                "12:00",
                "camera_1"
        ))).isNotNull();
    }

    @Test
    void whenSaved_findByNumberPlate() {
        vehicleRepository.save(new Vehicle(
                "LA51 ABC",
                "12:00",
                "camera_1"
        ));
        List<Vehicle> vehicles = vehicleRepository.findByNumberPlate("LA51 ABC");
        assertThat(vehicles).isNotEmpty();
    }

    @Test
    void clear_database() {
        for (Vehicle vehicle : vehicleRepository.findAll()) {
            LOGGER.info("VEHICLE:- " + vehicle.toString());
        }
        assertThat(vehicleRepository.count()).isEqualTo(0);
    }
}
