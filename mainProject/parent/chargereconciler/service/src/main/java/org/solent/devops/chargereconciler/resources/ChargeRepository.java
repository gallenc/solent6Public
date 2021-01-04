package org.solent.devops.chargereconciler.resources;

import org.solent.devops.chargereconciler.models.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {

    List<Charge> findByTimeGreaterThan(LocalTime time);

    Charge findByTime(LocalTime time);

}
