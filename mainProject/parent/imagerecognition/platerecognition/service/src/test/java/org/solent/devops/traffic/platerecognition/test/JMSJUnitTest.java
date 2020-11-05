/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.devops.traffic.platerecognition.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.solent.devops.message.jms.SimpleJmsSender;
import org.solent.devops.traffic.platerecognition.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig-service-test.xml"})
public class JMSJUnitTest {

    final static Logger LOG = LogManager.getLogger(JMSJUnitTest.class);

    @Autowired
    SimpleJmsSender simpleJmsSender;

    @Before
    public void setUp() {
        assertNotNull(simpleJmsSender);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() {

        for (int i = 0; i < 100; i++) {
            LOG.debug("sending Message: " + i);

            String message = "MESSAGE " + i;
            simpleJmsSender.send(message);
        }

    }
}
