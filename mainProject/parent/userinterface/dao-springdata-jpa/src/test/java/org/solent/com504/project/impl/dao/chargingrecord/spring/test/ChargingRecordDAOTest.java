/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.spring.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class ChargingRecordDAOTest {

    final static Logger LOG = LogManager.getLogger(ChargingRecordDAOTest.class);

    private static final long HOUR_IN_MS = 1000*60*60;
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ChargingRecordDAO chargingRecordDAO = null;

    @Before
    public void before() {
        assertNotNull(chargingRecordDAO);

        chargingRecordDAO.deleteAll();
        List<ChargingRecord> crList = chargingRecordDAO.findAll();
        assertTrue(crList.isEmpty());
    }

    @Test
    public void testLoadcontext() {
        LOG.debug("start testLoadcontext");
        LOG.debug("end of testLoadcontext");
    }

    @Test
    @SuppressWarnings("empty-statement")
    public void testfindByUuid() {
        LOG.debug("start testfindByUuid");

        String uuid = UUID.randomUUID().toString();

        List<Date> startDates = null;
        try {
            startDates = Arrays.asList(df.parse("2020-01-01 09:00:00"),
                    df.parse("2020-01-01 10:00:00"),
                    df.parse("2020-01-01 11:00:00"),
                    df.parse("2020-01-01 12:00:00"),
                    df.parse("2020-01-01 13:00:00"
                    ));
        } catch (ParseException ex) {
            throw new IllegalStateException("problem parsing dates in test:",ex);
        }
        
        int noOfChargingRecords = 0;

        for (Date entryDate: startDates) {

            // send 10 cars
            for (int i = 0; i < 10; i++) {
                // create objects to marshal
                ChargingRecord chargingRecord = new ChargingRecord();
                chargingRecord.setUuid(uuid);
                chargingRecord.setCharge(1.1);
                chargingRecord.setChargeRate(10.5);
                chargingRecord.setEntryDate(entryDate);
                
                Date exitDate = new Date(entryDate.getTime()+ HOUR_IN_MS);
                chargingRecord.setExitDate(exitDate);

                String entryPhotoId = UUID.randomUUID().toString();
                chargingRecord.setEntryPhotoId(entryPhotoId);
                String entryLocation = "Southampton";
                chargingRecord.setEntryLocation(entryLocation);
                String exitLocation = "London";
                chargingRecord.setExitLocation(exitLocation);
                String exitPhotoId = UUID.randomUUID().toString();
                chargingRecord.setExitPhotoId(exitPhotoId);
                String numberPlate = "HAZ604" + i;
                chargingRecord.setNumberPlate(numberPlate);
                
                chargingRecordDAO.save(chargingRecord);
                noOfChargingRecords ++;

                uuid = UUID.randomUUID().toString();
            }
        }
        
        // test findAll();
        List<ChargingRecord> crList = chargingRecordDAO.findAll();
        assertEquals (noOfChargingRecords, crList.size());

        LOG.debug("end of testfindByUuid");
    }

}
