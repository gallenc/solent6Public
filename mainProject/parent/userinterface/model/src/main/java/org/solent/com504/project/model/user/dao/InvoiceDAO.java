package org.solent.com504.project.model.user.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.user.dto.Invoice;

/**
 *
 * @author joao-
 */
public interface InvoiceDAO {
    
    public Invoice findById(Long id);
    
    public Invoice save(Invoice invoice);
    
    public void deleteById(Long id);
    
    public void delete(Invoice invoice);
    
    public void deleteAll();
    
    public Invoice findPaidInvoice(LocalDateTime paymentDate);
    
    public List<Invoice> findPartyInvoices(Party party);
    
    
}