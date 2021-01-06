package org.solent.devops.chargereconciler.resources;

import org.solent.devops.chargereconciler.models.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String> {

    boolean existsByNumberplate(String numberplate);

    Vehicle findByNumberplate(String numberplate);

    void deleteByNumberplate(String numberplate);

}
