/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.chargingrecord.service;

import java.util.List;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;

/**
 *
 * @author cgallen
 */
public interface ChargingRecordService {
    
        public ChargingRecord findById(Long id);
    
    public ChargingRecord findByCar(String numberPlate);
    
    public ChargingRecord save(ChargingRecord chargingRecord);
    
    public List<ChargingRecord> findAll();
    
    public void deleteById(long id);
    
    public void delete(ChargingRecord chargingRecord);
    
    public void deleteAll();
    
}
