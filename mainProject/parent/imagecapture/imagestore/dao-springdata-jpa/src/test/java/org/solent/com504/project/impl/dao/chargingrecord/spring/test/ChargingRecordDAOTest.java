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

    private static final long HOUR_IN_MS = 1000 * 60 * 60;

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
    public void testDao() {
        LOG.debug("start testfindByUuid");

        String searchuuid = UUID.randomUUID().toString();

        List<String> numberPlateList = Arrays.asList("HAZ604", "HAZ605", "HAZ606", "HAZ607", "HAZ608");

        List<Date> startDates = null;
        try {
            startDates = Arrays.asList(df.parse("2020-01-01 09:00:00"),
                    df.parse("2020-01-01 10:00:00"),
                    df.parse("2020-01-01 11:00:00"),
                    df.parse("2020-01-01 12:00:00"),
                    df.parse("2020-01-01 13:00:00"
                    ));
        } catch (ParseException ex) {
            throw new IllegalStateException("problem parsing dates in test:", ex);
        }

        int countOfChargingRecords = 0;

        // first uuid to search for
        String uuid = searchuuid;
        for (Date entryDate : startDates) {

            // send cars
            for (String numberPlate : numberPlateList) {
                // create objects to marshal
                ChargingRecord chargingRecord = new ChargingRecord();

                chargingRecord.setNumberPlate(numberPlate);

                // first uuid will be searchuuid all rest will be random
                chargingRecord.setUuid(uuid);

                chargingRecord.setCharge(1.1);
                chargingRecord.setChargeRate(10.5);
                chargingRecord.setEntryDate(entryDate);

                Date exitDate = new Date(entryDate.getTime() + HOUR_IN_MS);
                chargingRecord.setExitDate(exitDate);

                String entryPhotoId = UUID.randomUUID().toString();
                chargingRecord.setEntryPhotoId(entryPhotoId);
                String entryLocation = "Southampton";
                chargingRecord.setEntryLocation(entryLocation);
                String exitLocation = "London";
                chargingRecord.setExitLocation(exitLocation);
                String exitPhotoId = UUID.randomUUID().toString();
                chargingRecord.setExitPhotoId(exitPhotoId);

                chargingRecord = chargingRecordDAO.save(chargingRecord);

                LOG.debug("charging record " + countOfChargingRecords + " : " + chargingRecord);
                countOfChargingRecords++;

                uuid = UUID.randomUUID().toString();
            }
        }
        LOG.debug("inserted charging records : " + countOfChargingRecords);

        // test findAll();
        List<ChargingRecord> chargingRecordList = chargingRecordDAO.findAll();
        assertEquals(countOfChargingRecords, chargingRecordList.size());

        // test number of charging records
        long recordNumber = chargingRecordDAO.totalRecords();
        assertEquals(countOfChargingRecords, recordNumber);

        // test findByUuid with incorrect uuid
        ChargingRecord cr1 = chargingRecordDAO.findByUuid(searchuuid);
        assertNotNull(cr1);

        // test findByUuid with incorrect uuid
        cr1 = chargingRecordDAO.findByUuid(UUID.randomUUID().toString());
        assertNull(cr1);

        // test find by numberplate no date range
        String numberPlate = numberPlateList.get(0);
        Date startDate = null;
        Date endDate = null;
        Integer page = null;
        Integer size = null;
        chargingRecordList = chargingRecordDAO.findByNumberPlate(numberPlate, startDate, endDate, page, size);
        LOG.debug("numberPlateList.size() " + numberPlateList.size() + " chargingRecordList.size(): " + chargingRecordList.size());
        assertEquals(numberPlateList.size(), chargingRecordList.size());

        // test total records by numberplate no date range
        long noRecords = chargingRecordDAO.totalRecordsByNumberPlate(numberPlate, startDate, endDate);
        LOG.debug("number of " + numberPlate + " number plates: " + noRecords);
        assertEquals(numberPlateList.size(), noRecords);

        // test find by numberplate within date range
        numberPlate = numberPlateList.get(0);
        startDate = startDates.get(0);
        endDate = startDates.get(1);
        page = null;
        size = null;
        chargingRecordList = chargingRecordDAO.findByNumberPlate(numberPlate, startDate, endDate, page, size);
        LOG.debug(" numberPlate " +  numberPlate + " startDate:"+ startDate 
                + " endDate: " + endDate+" chargingRecordList.size():"+chargingRecordList.size());
        assertEquals(1, chargingRecordList.size());

        // test total records by numberplate within date range
        noRecords = chargingRecordDAO.totalRecordsByNumberPlate(numberPlate, startDate, endDate);
        LOG.debug(" numberPlate " +  numberPlate + " startDate:"+ startDate 
                + " endDate: " + endDate+" noRecords:"+noRecords);
        assertEquals(1, noRecords);

        LOG.debug("end of testfindByUuid");
    }
    
    @Test
    @SuppressWarnings("empty-statement")
    public void testCreateCRs() {
    
        LOG.debug("new database initialising initial charging records");
                                    
            //random uuid
            String searchuuid = UUID.randomUUID().toString();
            
            //List of number plates
            List<String> numberPlateList = Arrays.asList("HAZ604","HAZ605","HAZ606","HAZ607","HAZ608");
            
            List<Date> entryDates = null;
            try
            {
                entryDates = Arrays.asList(df.parse("2020-01-01 21:57:00"),
                        df.parse("2020-01-01 09:30:00"),
                        df.parse("2020-01-01 10:00:00"),
                        df.parse("2020-01-01 10:30:00"),
                        df.parse("2020-01-01 11:00:00"),
                        df.parse("2020-01-01 11:30:00"),
                        df.parse("2020-01-01 12:00:00"),
                        df.parse("2020-01-01 12:30:00"),
                        df.parse("2020-01-01 13:00:00"),
                        df.parse("2020-01-01 13:30:00"));
            }
            catch (ParseException ex) {
                throw new IllegalStateException("problem parsing dates:", ex);
            }
            
            int countOfChargingRecords = 0;
            
            String uuid = searchuuid;
            
            for(Date entryDate : entryDates)
            {                
                for(String numberPlate : numberPlateList)
                {
                    ChargingRecord chargingRecord = new ChargingRecord();
                    
                    chargingRecord.setNumberPlate(numberPlate);
                    //random uuid
                    chargingRecord.setUuid(uuid);
                    chargingRecord.setCharge(1.1);
                    chargingRecord.setChargeRate(10.5);
                    chargingRecord.setEntryDate(entryDate);

                    Date exitDate = new Date(entryDate.getTime() + HOUR_IN_MS);
                    chargingRecord.setExitDate(exitDate);

                    String entryPhotoId = UUID.randomUUID().toString();
                    chargingRecord.setEntryPhotoId(entryPhotoId);

                    chargingRecord.setEntryLocation("Southampton");
                    chargingRecord.setExitLocation("London");

                    String exitPhotoId = UUID.randomUUID().toString();
                    chargingRecord.setExitPhotoId(exitPhotoId);

                    LOG.debug("CHARGING RECORD CREATED: " + chargingRecord.toString());
                    
                    chargingRecord = chargingRecordDAO.save(chargingRecord);            
                    LOG.debug("CHARGING RECORD CREATED: " + chargingRecord.toString());
                    countOfChargingRecords++;
                    
                    //uuid = UUID.randomUUID().toString();
                }
            }
            LOG.debug("Inserted " + countOfChargingRecords + " charging records");
        
    }

}
