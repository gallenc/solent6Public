package org.solent.com504.project.impl.dao.user.springdata;

import org.solent.com504.project.model.user.dto.ChargingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joao-
 */
public interface ChargingRecordRepository  extends JpaRepository<ChargingRecord, Long> {


}