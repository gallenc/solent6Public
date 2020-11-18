/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.user.spring;

/**
 *
 * @author joao-
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.solent.com504.project.impl.dao.user.springdata.InvoiceRepository;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.user.dao.InvoiceDAO;
import org.solent.com504.project.model.user.dao.UserDAO;
import org.solent.com504.project.model.user.dto.Invoice;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InvoiceDAOImplSpring implements InvoiceDAO{
    
      @Autowired
    private InvoiceRepository invoiceRepository = null;

    @Override
    public Invoice findById(Long id) {
        Optional<Invoice> o = invoiceRepository.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        return null;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
    
    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Invoice invoice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Invoice findPaidInvoice(LocalDateTime paymentDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Invoice> findPartyInvoices(Party party) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
