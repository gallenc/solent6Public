package org.solent.com504.project.impl.dao.user.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.chargingrecord.spring.ChargingRecordDAOImplSpring;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class ChargingRecordDAOImplSpringTest {

    @Autowired
    private ChargingRecordDAOImplSpring chargingRecordDAO;

    private ChargingRecord generatedChargingRecord;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedChargingRecord = chargingRecordDAO.save(generateDummyChargingRecord());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        chargingRecordDAO.deleteById(generatedChargingRecord.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        ChargingRecord result = chargingRecordDAO.findById(generatedChargingRecord.getId());
        assertNotNull(result);
        assertEquals(generatedChargingRecord.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testSave() {
        ChargingRecord savedResult = chargingRecordDAO.save(generateDummyChargingRecord());
        assertNotNull(savedResult);
        assertNotNull(savedResult.getId());

        chargingRecordDAO.deleteById(savedResult.getId()); // delete so that db doesn't have dummy records
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<ChargingRecord> results = chargingRecordDAO.findAll();
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedChargingRecord));
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