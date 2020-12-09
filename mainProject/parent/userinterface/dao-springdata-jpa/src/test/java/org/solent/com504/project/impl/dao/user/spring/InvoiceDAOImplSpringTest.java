package org.solent.com504.project.impl.dao.user.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.invoice.spring.InvoiceDAOImplSpring;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class InvoiceDAOImplSpringTest {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private InvoiceDAOImplSpring invoiceDAOImpl;

    private Invoice generatedInvoice;

    @Before
    @Transactional
    public void setUp() {
        generatedInvoice = invoiceDAOImpl.save(generateDummyInvoice());
    }

    @After
    @Transactional
    public void tearDown() {
        invoiceDAOImpl.deleteById(generatedInvoice.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Invoice result = invoiceDAOImpl.findById(generatedInvoice.getId());
        assertNotNull(result);
        assertEquals(generatedInvoice.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<Invoice> results = invoiceDAOImpl.findAll();
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedInvoice));
    }

    @Test
    @Transactional
    public void testSave() {
        Invoice savedResult = invoiceDAOImpl.save(generateDummyInvoice());
        assertNotNull(savedResult);
        assertNotNull(savedResult.getId());
    }

    private Invoice generateDummyInvoice() {
        Invoice invoice = new Invoice();
        invoice.setAmount(100.00);       
        try {
            Date d = df.parse("2020-01-01 13:30:00");      
            invoice.setIssueDate(d);
        } catch (ParseException ex) {
            Logger.getLogger(InvoiceDAOImplSpringDeleteTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
        Date d = new Date();
        invoice.setPaidDate(d);
        return invoice;
    }
}