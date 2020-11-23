/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.spring;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.chargingrecord.springdata.ChargingRecordRepository;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component
public class ChargingRecordDAOImplSpring implements ChargingRecordDAO {

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
    public List<ChargingRecord> findByNumberPlate(String numberPlate, Date startDate, Date endDate, Integer page, Integer size) {
        int m_page = (page == null) ? 0 : page;
        int m_size = (page == null) ? 1 : size;

        Pageable pageable = PageRequest.of(m_page, m_size, Sort.by("startDate").ascending());
        Page p = recordsRepo.findByNumberPlate(numberPlate, startDate, endDate, pageable);
        return p.toList();
    }

    @Override
    public ChargingRecord findByUuid(String uuid) {
        return recordsRepo.findByUuid(uuid); 
    }

    @Override
    public ChargingRecord save(ChargingRecord chargingRecord) {
        return recordsRepo.save(chargingRecord);
    }

    @Override
    public Long numberOfRecordsByNumberPlate(String numberPlate, Date startDate, Date endDate) {
        Pageable pageable = PageRequest.of(0, 1);
        Page p = recordsRepo.findByNumberPlate(numberPlate, startDate, endDate, pageable);
        return p.getTotalElements();

    }

    @Override
    public Long numberOfRecords() {
        Pageable pageable = PageRequest.of(0, 1);
        Page p = recordsRepo.findAll(pageable);
        return p.getTotalElements();
    }

    @Override
    public List<ChargingRecord> findAll(Integer page, Integer size) {
        int m_page = (page == null) ? 0 : page;
        int m_size = (page == null) ? 1 : size;

        Pageable pageable = PageRequest.of(m_page, m_size, Sort.by("startDate").ascending());
        Page p = recordsRepo.findAll(pageable);

        List<ChargingRecord> result = p.toList();
        return result;

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

    @Override
    public List<ChargingRecord> findAll() {
        return recordsRepo.findAll();
    }

}
