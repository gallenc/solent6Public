/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.invoice.spring;

/**
 *
 * @author joao-
 */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.invoice.springdata.InvoiceRepository;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.invoice.dao.InvoiceDAO;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InvoiceDAOImplSpring implements InvoiceDAO{
    
      @Autowired
    private InvoiceRepository invoiceRepo = null;

    @Override
    public Invoice findById(Long id) {
        Optional<Invoice> o = invoiceRepo.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        return null;
    }
    
     @Override
    public List<Invoice> findAll() {
        return invoiceRepo.findAll();
    }
    
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }
    
    @Override
    public void deleteById(Long id) {
        invoiceRepo.deleteById(id);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceRepo.delete(invoice);
    }

    @Override
    public void deleteAll() {
        invoiceRepo.deleteAll();
    }

    @Override
    public Invoice findPaidInvoice(LocalDateTime paymentDate) {
//          return invoiceRepo.findPaidInvoice(paymentDate);
            throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Invoice> findPartyInvoices(Party party) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   
    
}
