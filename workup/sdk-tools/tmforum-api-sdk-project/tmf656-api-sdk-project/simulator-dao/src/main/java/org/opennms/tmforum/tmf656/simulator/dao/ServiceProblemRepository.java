package org.opennms.tmforum.tmf656.simulator.dao;

import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProblemRepository extends JpaRepository<ServiceProblemEntity, Long>, JpaSpecificationExecutor<ServiceProblemEntity> {

}


