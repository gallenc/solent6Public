/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.invoice.springdata;

/**
 *
 * @author joao-
 */


import java.time.LocalDateTime;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

//    @Query("select i from Invoice i where i.paidDate = :paidDate")
//    public Invoice findPaidInvoice(@Param("paidDate") LocalDateTime paidDate);

}
    