package org.solent.com504.project.impl.dao.chargingrecord.springdata;

import java.awt.print.Pageable;
import java.util.Date;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joao-
 */
public interface ChargingRecordRepository  extends JpaRepository<ChargingRecord, Long> {
    
    /*@Query("SELECT cr FROM ChargingRecord cr WHERE cr.numberPlate = :numberPlate and cr.entryDate >= :entryDate and cr.exitDate <= :exitDate")
    public Page<ChargingRecord> findByNumberPlate(@Param("numberPlate")String numberPlate,
            @Param("entryDate") Date startDate, 
            @Param("exitDate")Date endDate, Pageable pageable);*/
    
    /*@Query("SELECT cr FROM ChargingRecord cr WHERE cr.uuid = :uuid")
    public ChargingRecord findByUuid(@Param("uuid") String uuid);*/
}