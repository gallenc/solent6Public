/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.spring;

import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.chargingrecord.springdata.ChargingRecordRepository;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component
public class ChargingRecordDAOImplSpring implements ChargingRecordDAO{

    @Autowired
    private ChargingRecordRepository recordsRepo = null;
    
    @Override
    public ChargingRecord findById(Long id) {
        Optional<ChargingRecord> o = recordsRepo.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        return null;
    }
    
    @Override
    public ChargingRecord findByCar(String numberPlate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ChargingRecord save(ChargingRecord chargingRecord) {
        return recordsRepo.save(chargingRecord);
    }

    @Override
    public List<ChargingRecord> findAll() {
        return recordsRepo.findAll();
    }

    @Override
    public void deleteById(long id) {
        recordsRepo.deleteById(id);
    }

    @Override
    public void delete(ChargingRecord chargingRecord) {
        recordsRepo.delete(chargingRecord);
    }

    @Override
    public void deleteAll() {
        recordsRepo.deleteAll();
    }

    
}
