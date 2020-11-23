/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.chargingrecord.spring.test;

import org.solent.com504.project.impl.dao.user.spring.test.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.user.dao.RoleDAO;
import org.solent.com504.project.model.user.dao.UserDAO;
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

    @Autowired
    private ChargingRecordDAO chargingRecordDAO = null;

    @Before
    public void before() {
        assertNotNull(chargingRecordDAO);
    }

    @Test
    public void testLoadcontext() {
        LOG.debug("start testLoadcontext");
        LOG.debug("end of testLoadcontext");
    }
    //TODO TEST
}
