/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.user.springdata;

import java.util.List;
import org.solent.com504.project.model.user.dto.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ruipi
 */
public interface BankRepository extends JpaRepository<Bank, Long>{
    
    public Bank findByCardNumber(String cardNumber);
    
    @Query("select b from Bank b where b.cardNumber = :cardNumber")
    public List<Bank> findByCard(@Param("cardNumber") String cardNumber);
    
}
