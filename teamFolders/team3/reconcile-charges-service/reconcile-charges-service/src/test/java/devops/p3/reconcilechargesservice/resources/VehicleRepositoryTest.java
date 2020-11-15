package devops.p3.reconcilechargesservice.resources;

import devops.p3.reconcilechargesservice.models.Vehicle;
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
                "123e4567-e89b-12d3-a456-556642440000",
                "1",
                "2020-11-05T17:24:30.000+00:00",
                "PP587AO",
                ""
        ))).isNotNull();
    }

    @Test
    void whenSaved_findByNumberPlate() {
        vehicleRepository.save(new Vehicle(
                "123e4567-e89b-12d3-a456-556642440000",
                "1",
                "2020-11-05T17:24:30.000+00:00",
                "PP587AO",
                ""
        ));
        List<Vehicle> vehicles = vehicleRepository.findByNumberplate("PP587AO");
        assertThat(vehicles).isNotEmpty();
    }

    @Test
    void clear_database() {
        vehicleRepository.deleteAll();
        assertThat(vehicleRepository.count()).isEqualTo(0);
    }
}
