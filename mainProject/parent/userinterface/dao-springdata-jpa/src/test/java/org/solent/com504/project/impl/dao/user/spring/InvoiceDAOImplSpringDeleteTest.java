package org.solent.com504.project.impl.dao.user.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.invoice.spring.InvoiceDAOImplSpring;
import org.solent.com504.project.impl.dao.invoice.springdata.InvoiceRepository;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class InvoiceDAOImplSpringDeleteTest {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private InvoiceDAOImplSpring invoiceDAOImpl;

    @Autowired
    private InvoiceRepository repository;

    @Test
    @Transactional
    public void testDeleteById() {
        Invoice savedResult = invoiceDAOImpl.save(generateDummyInvoice());
        invoiceDAOImpl.deleteById(savedResult.getId());
        assertNull(invoiceDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDelete() {
        Invoice savedResult = invoiceDAOImpl.save(generateDummyInvoice());
        invoiceDAOImpl.delete(savedResult);
        assertNull(invoiceDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        invoiceDAOImpl.deleteAll();
        assertTrue(repository.findAll().isEmpty());
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
