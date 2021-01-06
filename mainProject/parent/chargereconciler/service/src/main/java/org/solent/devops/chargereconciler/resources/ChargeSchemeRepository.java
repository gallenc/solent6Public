package org.solent.devops.chargereconciler.resources;

import org.hibernate.mapping.List;
import org.solent.devops.chargereconciler.models.Charge;
import org.solent.devops.chargereconciler.models.ChargeScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ChargeSchemeRepository extends JpaRepository<ChargeScheme, Long> {

}
