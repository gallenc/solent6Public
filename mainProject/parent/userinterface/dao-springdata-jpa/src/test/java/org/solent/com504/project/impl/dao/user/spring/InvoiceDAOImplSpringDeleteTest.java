package org.solent.com504.project.impl.dao.user.spring;

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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class InvoiceDAOImplSpringDeleteTest {


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
        invoice.setAmmount(100.00);
        invoice.setIssueDate(LocalDateTime.now().minusDays(2));
        invoice.setPaidDate(LocalDateTime.now());
        return invoice;
    }
}
