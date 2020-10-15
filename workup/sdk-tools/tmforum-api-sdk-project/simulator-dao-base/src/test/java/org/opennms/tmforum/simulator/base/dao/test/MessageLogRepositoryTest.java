package org.opennms.tmforum.simulator.base.dao.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.opennms.tmforum.simulator.base.dao.MessageLogRepository;
import org.opennms.tmforum.simulator.base.dao.MessageLoggingService;
import org.opennms.tmforum.simulator.base.model.MessageLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * NOTE FOR DAO TEST IN ECLIPSE TO PASS YOU NEED TO TURN OFF PROJECT REFERENCING
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-base-dao-test-context.xml" })
public class MessageLogRepositoryTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(MessageLogRepositoryTest.class);

    @Autowired
    private MessageLogRepository messageLogRepository = null;
    
    @Autowired
    private MessageLoggingService messageLoggingService = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(messageLogRepository);
        messageLoggingService.deleteLogMessages();
        LOG.debug("before test complete");
    }

    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of messageLog test1");

        for (int i = 1; i<=10; i++) {
            String url= "http://this";
            String method="GET";
            String body="{id:"+i + "}";
            messageLoggingService.logInNotification(body);
            messageLoggingService.logOutNotification(body);
            messageLoggingService.logRequest(url, method, body);
            messageLoggingService.logReply(url, method, body);
        }

       List<MessageLog> messages = messageLogRepository.findAll();
        assertEquals(40,messages.size());
        
        
        messageLoggingService.deleteLogMessages();
        messages = messageLogRepository.findAll();
        assertTrue(messages.isEmpty());

        
        LOG.debug("end of messageLog test1");
    }
}