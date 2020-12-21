/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.web;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.solent.com504.project.model.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author joao-
 */
@Controller
@Transactional
public class InvoiceController {
    
    final static Logger LOG = LogManager.getLogger(UserController.class);

    {
        LOG.debug("UserController created");
    }

    @Autowired
    private InvoiceService invoiceService;
    
    @RequestMapping(value = {"/invoice"}, method = RequestMethod.GET)
    public String invoice(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoiceList", invoices);
        return "invoice";
    }
    
}
