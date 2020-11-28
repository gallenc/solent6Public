package org.solent.com504.project.impl.dao.user.spring;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.user.springdata.ChargingRecordRepository;
import org.solent.com504.project.impl.dao.user.springdata.InvoiceRepository;
import org.solent.com504.project.model.user.dao.ChargingRecordDAO;
import org.solent.com504.project.model.user.dto.ChargingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component
public class ChargingRecordDAOImplSpring implements ChargingRecordDAO{
    
    public static final int DEFAULT_PAGE_SIZE=20;

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

    @Override
    public List<ChargingRecord> findByNumberPlate(String numberPlate, Date entryDate, Date exitDate, Integer page, Integer size) {
        /*int m_page = (page == null) ? 0 : page;
        int m_size = (size == null) ? DEFAULT_PAGE_SIZE : size;
        Date m_entryDate = (entryDate == null) ? new Date(0) : entryDate;
        Date m_exitDate = (exitDate == null) ? new Date() : exitDate;

        Pageable pageable = (Pageable) PageRequest.of(m_page, m_size, Sort.by("entryDate").ascending());

        Page p = recordsRepo.findByNumberPlate(numberPlate, m_entryDate, m_exitDate, pageable);
        return p.toList();*/
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long totalRecordsByNumberPlate(String numberPlate, Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ChargingRecord findByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ChargingRecord> findAll(Integer page, Integer size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long totalRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
}