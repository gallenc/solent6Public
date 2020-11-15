package devops.p3.reconcilechargesservice.resources;

import devops.p3.reconcilechargesservice.models.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

    List<Vehicle> findByNumberplate(String numberplate);

}
