package org.opennms.tmforum.tmf656.simulator.dao.test;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.opennms.tmforum.swagger.tmf656.swagger.model.Note;
import org.opennms.tmforum.swagger.tmf656.swagger.model.RelatedEntityRef;
import org.opennms.tmforum.tmf656.simulator.dao.ServiceProblemRepository;
import org.opennms.tmforum.tmf656.simulator.model.ServiceProblemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * NOTE FOR DAO TEST IN ECLIPSE TO PASS YOU NEED TO TURN OFF PROJECT REFERENCING
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-dao-test-context.xml" })
public class ServiceProblemRepositoryTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(ServiceProblemRepositoryTest.class);

    @Autowired
    private ServiceProblemRepository serviceProblemRepository = null;

    @Before
    public void before() {
        LOG.debug("before test running");
        assertNotNull(serviceProblemRepository);
        LOG.debug("before test complete");
    }

    @Transactional
    @Test
    public void test1() {
        LOG.debug("start of test1");

        ServiceProblemEntity serviceProblem1 = new ServiceProblemEntity();
        
        RelatedEntityRef firstAlert= new  RelatedEntityRef();
        firstAlert.setId("1");
        firstAlert.setName("firstAlertName");
        firstAlert.setType("firstAlertType");
        serviceProblem1.setFirstAlert(firstAlert);
        
        List<Note> comment = new ArrayList<Note>();
        Note note = new Note();
        note.setAuthor("fred Blogs");
        OffsetDateTime date = OffsetDateTime.now();
        note.setDate(date );
        comment.add(note );
        
        Note note2 = new Note();
        note.setAuthor("Jimmy Cricket");
        OffsetDateTime date2 = OffsetDateTime.now();
        note.setDate(date2 );
        comment.add(note2 );
        
        serviceProblem1.setComment(comment );

        serviceProblem1 = serviceProblemRepository.save(serviceProblem1);
        LOG.debug("serviceProblem1=" + serviceProblem1);

        Long id = serviceProblem1.getId();
        ServiceProblemEntity serviceProblem2 = serviceProblemRepository.getOne(id);
        LOG.debug("serviceProblem2=" + serviceProblem2);
        
        // check saved and restored are equal
        assertTrue(serviceProblem1.toString().equals(serviceProblem1.toString()));
        
        LOG.debug("end of test1");
    }
}