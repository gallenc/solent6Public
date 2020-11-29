/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.chargingrecord.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.chargingrecord.service.ChargingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author joao-
 */
@Service
public class ChargingRecordServiceImpl implements ChargingRecordService {
    
@Autowired
    private ChargingRecordDAO chargingRecordDAO;

    @Override
    public ChargingRecord findById(Long id) {
        return chargingRecordDAO.findById(id);
    }

    @Override
    public List<ChargingRecord> findByNumberPlate(String numberPlate, Date startDate, Date endDate, Integer page, Integer size) {
        return chargingRecordDAO.findByNumberPlate(numberPlate, startDate, endDate, page, size);

    }

    @Override
    public Long totalRecordsByNumberPlate(String numberPlate, Date startDate, Date endDate) {
        return chargingRecordDAO.totalRecordsByNumberPlate(numberPlate, startDate, endDate);
    }

    @Override
    public ChargingRecord findByUuid(String uuid) {
        return chargingRecordDAO.findByUuid(uuid);
    }

    @Override
    public ChargingRecord save(ChargingRecord chargingRecord) {
        return chargingRecordDAO.save(chargingRecord);
    }

    @Override
    public List<ChargingRecord> findAll(Integer page, Integer size) {
        return chargingRecordDAO.findAll(page, size);
    }

    @Override
    public Long totalRecords() {
        return chargingRecordDAO.totalRecords();
    }

    @Override
    public List<ChargingRecord> findAll() {
        return chargingRecordDAO.findAll();

    }

    @Override
    public void deleteById(long id) {
        chargingRecordDAO.deleteById(id);
    }

    @Override
    public void delete(ChargingRecord chargingRecord) {
        chargingRecordDAO.delete(chargingRecord);
    }

    @Override
    public void deleteAll() {
        chargingRecordDAO.deleteAll();
    }
}