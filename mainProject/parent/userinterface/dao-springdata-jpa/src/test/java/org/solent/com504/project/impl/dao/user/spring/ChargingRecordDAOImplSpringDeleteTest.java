package org.solent.com504.project.impl.dao.user.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.chargingrecord.spring.ChargingRecordDAOImplSpring;
import org.solent.com504.project.impl.dao.chargingrecord.springdata.ChargingRecordRepository;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class ChargingRecordDAOImplSpringDeleteTest {

    @Autowired
    private ChargingRecordDAOImplSpring chargingRecordDAO;

    @Autowired
    private ChargingRecordRepository repository;

    @Test
    public void testDeleteById() {
        ChargingRecord savedResult = chargingRecordDAO.save(generateDummyChargingRecord());
        chargingRecordDAO.deleteById(savedResult.getId());
        assertNull(chargingRecordDAO.findById(savedResult.getId()));
    }

    @Test
    public void testDelete() {
        ChargingRecord savedResult = chargingRecordDAO.save(generateDummyChargingRecord());
        chargingRecordDAO.delete(savedResult);
        assertNull(chargingRecordDAO.findById(savedResult.getId()));
    }

    @Test
    public void testDeleteAll() {
        chargingRecordDAO.deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private ChargingRecord generateDummyChargingRecord() {
        ChargingRecord chargingRecord = new ChargingRecord();
        chargingRecord.setNumberPlate("US-FORD-1001");
        chargingRecord.setCharge(60.00);
        chargingRecord.setChargeRate(10.00);
        chargingRecord.setEntryDate(new Date());
        chargingRecord.setEntryLocation("TX");
        chargingRecord.setEntryPhotoId("12345678");
        chargingRecord.setExitDate(new Date());
        return chargingRecord;
    }
}
