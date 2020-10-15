package org.opennms.tmforum.simulator.base.dao;


import org.opennms.tmforum.simulator.base.model.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {

}
