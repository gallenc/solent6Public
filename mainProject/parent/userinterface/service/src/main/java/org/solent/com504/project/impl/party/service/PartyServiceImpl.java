/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.party.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.solent.com504.project.model.party.dao.PartyDAO;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.solent.com504.project.model.party.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyDAO partyDao;

    @Override
    public Party findById(Long id) {
        return partyDao.findById(id);
    }

    @Override
    public Party save(Party party) {
        return partyDao.save(party);
    }

    @Override
    public List<Party> findAll() {
        return partyDao.findAll();
    }

    @Override
    public void deleteById(long id) {
        partyDao.deleteById(id);
    }

    @Override
    public void delete(Party party) {
        partyDao.delete(party);
    }

    @Override
    public void deleteAll() {
        partyDao.deleteAll();
    }

    @Override
    public List<Party> findByPartyRole(PartyRole partyRole) {
        return partyDao.findByPartyRole(partyRole);
    }

    @Override
    public List<Party> findByName(String firstName, String secondName) {
        return partyDao.findByName(firstName, secondName);
    }

    @Override
    public Party findByUuid(String uuid) {
         return partyDao.findByUuid(uuid);
    }

    @Override
    public List<PartyRole> getAvailablePartyRoles() {
        return Arrays.asList(PartyRole.values());
    }

//    @Override
//    public Party findByNumberPlate(String numberPlate) {
//        return partyDao.findByNumberPlate(numberPlate);
//    }
    @Override
    public Party findPartyByUsername(String username) {
        return partyDao.findByUsername(username);
    }

    @Override
    public Set<Party> findAllParties() {
        return partyDao.findAllParties();
    }
}
