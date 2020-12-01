package org.solent.com504.project.impl.dao.user.spring;

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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class InvoiceDAOImplSpringTest {

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
        invoice.setAmmount(100.00);
        invoice.setIssueDate(LocalDateTime.now().minusDays(2));
        invoice.setPaidDate(LocalDateTime.now());
        return invoice;
    }
}