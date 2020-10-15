#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.${tmfSpecPackageName}.simulator.dao;

import org.opennms.tmforum.${tmfSpecPackageName}.simulator.model.ServiceProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProblemRepository extends JpaRepository<ServiceProblemEntity, Long>, JpaSpecificationExecutor<ServiceProblemEntity> {

}


