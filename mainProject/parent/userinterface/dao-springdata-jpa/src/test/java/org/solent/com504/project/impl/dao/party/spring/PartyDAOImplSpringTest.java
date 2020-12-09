package org.solent.com504.project.impl.dao.party.spring;

import org.junit.After;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class PartyDAOImplSpringTest {

    @Autowired
    private PartyDAOImplSpring partyDAOImplSpring;

    private Party generatedParty;

    @Before
    @Transactional
    public void setUp() {
        generatedParty = partyDAOImplSpring.save(generateDummyParty());
    }

    @After
    @Transactional
    public void tearDown() {
        partyDAOImplSpring.deleteById(generatedParty.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Party result = partyDAOImplSpring.findById(generatedParty.getId());
        assertNotNull(result);
        assertEquals(generatedParty.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<Party> resultList = partyDAOImplSpring.findAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
    }

    @Test(expected = Exception.class)
    @Transactional
    public void testDelete() {
        Party savedParty = partyDAOImplSpring.save(generateDummyParty());
        partyDAOImplSpring.delete(savedParty);
        partyDAOImplSpring.findById(savedParty.getId());
    }

    @Test
    @Transactional
    public void testFindByPartyRole() {
        List<Party> results = partyDAOImplSpring.findByPartyRole(PartyRole.BUYER);
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedParty));
    }

    @Test
    @Transactional
    public void testFindByName() {
        Set<Party> results = partyDAOImplSpring.findByName(generatedParty.getFirstName(), generatedParty.getSecondName());
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedParty));
    }

    @Test
    @Transactional
    public void testFindByUuid() {
        Party result = partyDAOImplSpring.findByUuid(generatedParty.getUuid());
        assertNotNull(result);
        assertEquals(generatedParty.getUuid(), result.getUuid());
    }

    private Party generateDummyParty() {
        Party party = new Party();
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