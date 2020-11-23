/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.springdata;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author joao-
 */
public interface ChargingRecordRepository extends JpaRepository<ChargingRecord, Long> {

    @Query("SELECT cr FROM ChargingRecord cr WHERE cr.numberPlate = :numberPlate and u.startDate >= :startDate and u.endDate <= :endDate")
    public Page<ChargingRecord> findByNumberPlate(@Param("numberPlate")String numberPlate,
            @Param("startDate") Date startDate, 
            @Param("endDate")Date endDate, Pageable pageable);
    
    @Query("SELECT cr FROM ChargingRecord cr WHERE cr.uuid = :numberPlate")
    public ChargingRecord findByUuid(@Param("uuid") String uuid);
    
}
