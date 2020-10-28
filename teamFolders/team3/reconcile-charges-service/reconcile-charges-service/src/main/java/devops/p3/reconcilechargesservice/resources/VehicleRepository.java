package devops.p3.reconcilechargesservice.resources;

import devops.p3.reconcilechargesservice.models.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    List<Vehicle> findByNumberPlate(String number_plate);

    Vehicle findById(long id);
}
