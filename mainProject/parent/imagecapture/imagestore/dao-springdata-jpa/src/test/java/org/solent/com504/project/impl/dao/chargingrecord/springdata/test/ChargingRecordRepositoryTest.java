/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.springdata.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.chargingrecord.springdata.ChargingRecordRepository;
import org.solent.com504.project.model.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class ChargingRecordRepositoryTest {

    final static Logger LOG = LogManager.getLogger(ChargingRecordRepositoryTest.class);

    @Autowired
    private ChargingRecordRepository chargingRecordRepository = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(chargingRecordRepository);
        LOG.debug("before test complete");
    }

    
    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        ChargingRecord chargingRecord1 = new ChargingRecord();
        chargingRecord1 = chargingRecordRepository.save(chargingRecord1);
        System.out.println("chargingRecord1=" + chargingRecord1);

        Long id = chargingRecord1.getId();
        ChargingRecord chargingRecord2 = chargingRecordRepository.getOne(id);
        System.out.println("chargingRecord2=" + chargingRecord2);
        LOG.debug("end of test1");
    }
}
