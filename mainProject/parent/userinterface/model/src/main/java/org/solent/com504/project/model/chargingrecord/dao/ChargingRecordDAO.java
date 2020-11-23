/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.chargingrecord.dao;

import java.util.Date;
import java.util.List;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;


/**
 *
 * @author joao-
 */
public interface ChargingRecordDAO {
    
    public ChargingRecord findById(Long id);
    
    public List<ChargingRecord> findByNumberPlate(String numberPlate, Date startDate, Date endDate, Integer page, Integer size);

    public Long numberOfRecordsByNumberPlate(String numberPlate, Date startDate, Date endDate);

    public ChargingRecord findByUuid(String uuid);
    
    public ChargingRecord save(ChargingRecord chargingRecord);
    
    public List<ChargingRecord> findAll(Integer page, Integer size);
    
    public Long numberOfRecords();
    
    public List<ChargingRecord> findAll();
    
    public void deleteById(long id);
    
    public void delete(ChargingRecord chargingRecord);
    
    public void deleteAll();
}
