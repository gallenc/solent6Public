/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.user.service;

import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.dao.car.springdata.CarRepository;
import org.solent.com504.project.impl.dao.party.springdata.PartyRepository;
import org.solent.com504.project.impl.dao.invoice.springdata.InvoiceRepository;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.solent.com504.project.model.dto.Bank;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.invoice.dto.Invoice;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.User;
import org.solent.com504.project.model.user.dto.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cgallen
 */
public class DBInitialise {

    final static Logger LOG = LogManager.getLogger(DBInitialise.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void init() {

        // add all roles in model to database
        if (roleRepository.findAll().isEmpty()) {
            UserRoles[] allRoles = UserRoles.values();
            for (int i = 0; i < allRoles.length; i++) {
                String roleName = allRoles[i].name();
                LOG.debug("initialising user role " + roleName + " to database");
                Role role = new Role();
                role.setName(roleName);
                roleRepository.saveAndFlush(role);
            }
        }

        // add admin and simple user to database by default
        if (userRepository.findAll().isEmpty()) {
            LOG.debug("new database initialising default globaladmin and basicuser and partyadmin");
            User adminUser = new User();
            adminUser.setFirstName("globaladmin");
            adminUser.setSecondName("globaladmin");
            adminUser.setUsername("globaladmin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("globaladmin"));

            HashSet roles = new HashSet<>();
            roles.addAll(roleRepository.findByName(UserRoles.ROLE_USER.toString()));
            roles.addAll(roleRepository.findByName(UserRoles.ROLE_GLOBAL_ADMIN.toString()));

            adminUser.setRoles(roles);
            userRepository.saveAndFlush(adminUser);

            User basicUser = new User();
            basicUser.setFirstName("basicuser");
            basicUser.setSecondName("basicuser");
            basicUser.setUsername("basicuser");
            basicUser.setPassword(bCryptPasswordEncoder.encode("basicuser"));            
            roles = new HashSet<>();
            roles.addAll(roleRepository.findByName(UserRoles.ROLE_USER.toString()));
            basicUser.setRoles(roles);
            Bank bank = new Bank();
            bank.setCardNumber("56172517");
            bank.setSortCode("204025");
            bank.setCvv(352);
            basicUser.setBank(bank);
            userRepository.saveAndFlush(basicUser);
            
            User partyUser = new User();
            partyUser.setFirstName("partyuser");
            partyUser.setSecondName("partyuser");
            partyUser.setUsername("partyuser");
            partyUser.setPassword(bCryptPasswordEncoder.encode("partyuser"));         
            
            roles = new HashSet<>();
            roles.addAll(roleRepository.findByName(UserRoles.ROLE_USER.toString()));
            roles.addAll(roleRepository.findByName(UserRoles.ROLE_PARTY_ADMIN.toString()));            
            partyUser.setRoles(roles);
            
            Bank partyBank = new Bank();
            partyBank.setCardNumber("36985214");
            partyBank.setSortCode("604580");
            partyBank.setCvv(400);
            partyUser.setBank(partyBank);
            userRepository.saveAndFlush(partyUser);
        }

        // create a first basic Party
        if (partyRepository.findAll().isEmpty()) {
            LOG.debug("new database initialising first party owned by basic user");

            Party party = new Party();
            
            party.setFirstName("default_party");
            party.setSecondName("default_party");
            party = partyRepository.saveAndFlush(party);

            User user = userRepository.findByUsername("basicuser");
            User partyUser = userRepository.findByUsername("partyuser");
            LOG.debug("adding to party user:" + user);
            LOG.debug("adding to party partyUser" + partyUser);
            Set<User> users = new HashSet();
            users.add(user);
            users.add(partyUser);
            party.setUsers(users);            
            
            party = partyRepository.saveAndFlush(party);
            LOG.debug("added party to database:" + party);
        }
        
        if(invoiceRepository.findAll().isEmpty()){
        Invoice invoice = new Invoice();
        
        invoice.setAmmount(20.0);
        invoice.setId(50L);
        invoiceRepository.save(invoice);
        }

    }
}
