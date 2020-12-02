/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.invoice.service;

import java.time.LocalDateTime;
import java.util.List;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author joao-
 */
public interface InvoiceService {
    
    public Invoice findById(Long id);
    
    public List<Invoice> findAll();
    
    public Invoice save(Invoice invoice);
    
    public void deleteById(Long id);
    
    public void delete(Invoice invoice);
    
    public void deleteAll();
    
    public Invoice findPaidInvoice(LocalDateTime paymentDate);
    
    public List<Invoice> findPartyInvoices(Party party);
    
}
