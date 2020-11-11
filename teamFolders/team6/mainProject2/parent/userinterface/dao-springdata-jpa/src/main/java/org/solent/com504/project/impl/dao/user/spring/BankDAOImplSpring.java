/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.dao.user.spring;

import java.util.List;
import java.util.Optional;
import org.solent.com504.project.impl.dao.user.springdata.BankRepository;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.model.user.dao.BankDAO;
import org.solent.com504.project.model.user.dto.Bank;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ruipi
 */
public class BankDAOImplSpring implements BankDAO{
    
    @Autowired
    private BankRepository bankRepository = null;
    
    @Override
    public Bank findById(Long id) {
               Optional<Bank> bank = bankRepository.findById(id);
        if(bank.isPresent()){
            return bank.get();
        }
        return null;        
    }

    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        bankRepository.deleteById(id);
    }

    @Override
    public void deleteByCardNumber(String cardNumber) {
        bankRepository.deleteByCardNumber(cardNumber);
    }

    @Override
    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }

    @Override
    public void deleteAll() {
        bankRepository.deleteAll();
    }

    @Override
    public Bank findByCardNumber(String cardNumber) {
        return bankRepository.findByCardNumber(cardNumber);
    }
    
}
