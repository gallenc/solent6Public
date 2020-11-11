/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.user.dao;

import java.util.List;
import org.solent.com504.project.model.user.dto.Bank;

/**
 *
 * @author ruipi
 */
public interface BankDAO {
    
    public Bank findById(Long id);
    
    public Bank save(Bank bank);
    
    public List<Bank> findAll();
    
    public void deleteById(long id);
    
    public void deleteByCardNumber(String cardNumber);
    
    public void delete(Bank bank);
    
    public void deleteAll();
    
    public Bank findByCardNumber(String cardNumber);
    
}
