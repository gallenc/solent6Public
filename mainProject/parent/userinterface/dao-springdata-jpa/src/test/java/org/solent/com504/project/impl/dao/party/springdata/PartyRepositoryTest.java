package org.solent.com504.project.impl.dao.party.springdata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.party.dto.Party;
import org.solent.com504.project.model.party.dto.PartyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class PartyRepositoryTest {

    @Autowired
    private PartyRepository partyRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Transactional
    public void testFindByPartyRole() {
        Party party = generateDummyParty();
        Party savedParty = partyRepository.save(party);

        List<Party> results = partyRepository.findByPartyRole(PartyRole.BUYER);
        assertTrue(results.contains(savedParty));

        partyRepository.deleteById(savedParty.getId());
    }

    @Test
    @Transactional
    public void testFindByName() {
        Party party = generateDummyParty();
        Party savedParty = partyRepository.save(party);

        Set<Party> results = partyRepository.findByName("Night Party", "Out Door");
        assertTrue(results.contains(party));

        partyRepository.deleteById(savedParty.getId());
    }

    @Test
    @Transactional
    public void testFindByUuid() {
        Party party = generateDummyParty();
        Party savedParty = partyRepository.save(party);

        List<Party> results = partyRepository.findByUuid("123e4567-e89b-42d3-a456-556642440000");
        assertTrue(results.contains(party));

        partyRepository.deleteById(savedParty.getId());
    }

    @Test
    @Transactional
    public void findAll() {
        Party party = generateDummyParty();
        Party savedParty = partyRepository.save(party);

        List<Party> results = partyRepository.findAll();
        assertNotNull(results);
        assertTrue(results.size() > 0);

        partyRepository.deleteById(savedParty.getId());
    }

    private Party generateDummyParty() {
        Party party = new Party();
        party.setId(1L);

        Address address = new Address();
        address.setAddressLine1("address line 1");
        address.setAddressLine2("address line 2");
        address.setNumber("123");
        address.setCountry("USA");
        address.setPostcode("12345");
        party.setAddress(address);

        party.setPartyRole(PartyRole.BUYER);
        party.setFirstName("Night Party");
        party.setSecondName("Out Door");
        party.setUuid("123e4567-e89b-42d3-a456-556642440000");

        return party;
    }
}