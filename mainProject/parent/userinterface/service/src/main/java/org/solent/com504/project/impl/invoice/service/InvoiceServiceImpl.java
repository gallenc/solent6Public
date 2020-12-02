/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.invoice.service;

import java.time.LocalDateTime;
import java.util.List;
import org.solent.com504.project.model.chargingrecord.dao.ChargingRecordDAO;
import org.solent.com504.project.model.invoice.dao.InvoiceDAO;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.solent.com504.project.model.invoice.service.InvoiceService;
import org.solent.com504.project.model.party.dto.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author joao-
 */
@Component("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
    
    @Autowired
    private InvoiceDAO invoiceDao;
    
    @Override
    public Invoice findById(Long id) {
        return invoiceDao.findById(id);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    @Override
    public void deleteById(Long id) {
        invoiceDao.deleteById(id);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }

    @Override
    public void deleteAll() {
        invoiceDao.deleteAll();
    }

    @Override
    public Invoice findPaidInvoice(LocalDateTime paymentDate) {
        return invoiceDao.findPaidInvoice(paymentDate);
    }

    @Override
    public List<Invoice> findPartyInvoices(Party party) {
        return invoiceDao.findPartyInvoices(party);
    }
    
}
