/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.user.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.impl.dao.chargingrecord.springdata.ChargingRecordRepository;
import org.solent.com504.project.impl.dao.party.springdata.PartyRepository;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.solent.com504.project.model.chargingrecord.dto.ChargingRecord;
import org.solent.com504.project.model.party.dto.Party;
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
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static final long HOUR_IN_MS = 1000 * 60 * 60;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ChargingRecordRepository chargingRecordRepository;


    @Autowired
    private PartyRepository partyRepository;

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
            LOG.debug("new database initialising default globaladmin and basicuser");
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
            userRepository.saveAndFlush(basicUser);

        }

        // create a first basic Party
        if (partyRepository.findAll().isEmpty()) {
            LOG.debug("new database initialising first party owned by basic user");

            Party party = new Party();
            party.setFirstName("default_party");
            party.setSecondName("default_party");
            party = partyRepository.saveAndFlush(party);

            User user = userRepository.findByUsername("basicuser");
            LOG.debug("adding to party user:" + user);
            Set<User> users = new HashSet();
            users.add(user);
            party.setUsers(users);

            party = partyRepository.saveAndFlush(party);
            LOG.debug("added party to database:" + party);

        }
        
        //creating an initial charging record
        /*if(chargingRecordRepository.findAll().isEmpty()){
            LOG.debug("new database initialising initial charging records");
                                    
            //random uuid
            String searchuuid = UUID.randomUUID().toString();
            
            //List of number plates
            List<String> numberPlateList = Arrays.asList("HAZ604","HAZ605","HAZ606","HAZ607","HAZ608");
            
            Date entryDate;
            try {
                entryDate = df.parse("2020-01-01 09:00:00");
            } catch (ParseException ex) {
                throw new IllegalStateException("problem parsing dates in test:", ex);
            }
            
            ChargingRecord chargingRecord = new ChargingRecord();            
            
            chargingRecord.setNumberPlate("HAZ604");
            //random uuid
            chargingRecord.setUuid(searchuuid);
            chargingRecord.setCharge(1.1);
            chargingRecord.setChargeRate(10.5);
            chargingRecord.setEntryDate(entryDate);
            
            Date exitDate = new Date(entryDate.getTime() + HOUR_IN_MS);
            chargingRecord.setExitDate(exitDate);
            
            String entryPhotoId = UUID.randomUUID().toString();
            chargingRecord.setEntryPhotoId(entryPhotoId);
                        
            chargingRecord.setEntryLocation("Southampton");
            
            chargingRecord.setExitLocation("London");
            
            String exitPhotoId = UUID.randomUUID().toString();
            chargingRecord.setExitPhotoId(exitPhotoId);
            
            chargingRecordRepository.saveAndFlush(chargingRecord);                        
            LOG.debug("CHARGING RECORD CREATED: " + chargingRecord.toString());                        
        }*/
        
        if(chargingRecordRepository.findAll().isEmpty())
        {
            LOG.debug("new database initialising initial charging records");
                                    
            //random uuid
            String searchuuid = UUID.randomUUID().toString();
            
            //List of number plates
            List<String> numberPlateList = Arrays.asList("HAZ604","HAZ605","HAZ606","HAZ607","HAZ608");
            
            List<Date> entryDates = null;
            try
            {
                entryDates = Arrays.asList(df.parse("2020-01-01 21:57:00"),
                        df.parse("2020-01-01 09:30:00"),
                        df.parse("2020-01-01 10:00:00"),
                        df.parse("2020-01-01 10:30:00"),
                        df.parse("2020-01-01 11:00:00"),
                        df.parse("2020-01-01 11:30:00"),
                        df.parse("2020-01-01 12:00:00"),
                        df.parse("2020-01-01 12:30:00"),
                        df.parse("2020-01-01 13:00:00"),
                        df.parse("2020-01-01 13:30:00"));                                
            
            }
            catch (ParseException ex) {
                throw new IllegalStateException("problem parsing dates:", ex);
            }
            
            int countOfChargingRecords = 0;
            
            
            String uuid = searchuuid;
            
            for(Date entryDate : entryDates)
            {                
                for(String numberPlate : numberPlateList)
                {
                    ChargingRecord chargingRecord = new ChargingRecord();
                    
                    chargingRecord.setNumberPlate(numberPlate);
                    //random uuid
                    chargingRecord.setUuid(uuid);
                    chargingRecord.setCharge(1.1);
                    chargingRecord.setChargeRate(10.5);
                    chargingRecord.setEntryDate(entryDate);

                    Date exitDate = new Date(entryDate.getTime() + HOUR_IN_MS);
                    chargingRecord.setExitDate(exitDate);

                    String entryPhotoId = UUID.randomUUID().toString();
                    chargingRecord.setEntryPhotoId(entryPhotoId);

                    chargingRecord.setEntryLocation("Southampton");
                    chargingRecord.setExitLocation("London");

                    String exitPhotoId = UUID.randomUUID().toString();
                    chargingRecord.setExitPhotoId(exitPhotoId);

                    LOG.debug("CHARGING RECORD CREATED: " + chargingRecord.toString());
                    
                    chargingRecord = chargingRecordRepository.saveAndFlush(chargingRecord);            
                    LOG.debug("CHARGING RECORD CREATED: " + chargingRecord.toString());
                    countOfChargingRecords++;
                    
                    //uuid = UUID.randomUUID().toString();
                }
            }
            LOG.debug("Inserted " + countOfChargingRecords + " charging records");
        }
    }
}
