package org.solent.com504.project.impl.dao.user.springdata;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User generatedUser;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedUser = userRepository.save(generateDummyUser());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        userRepository.deleteById(generatedUser.getId());
    }

    @Test
    @Transactional
    public void findByUsername() {
        User result = userRepository.findByUsername(generatedUser.getUsername());
        assertNotNull(result);
        assertEquals(generatedUser.getUsername(), result.getUsername());
    }

    @Test
    @Transactional
    public void findByNames() {
        List<User> results = userRepository.findByNames(generatedUser.getFirstName(), generatedUser.getSecondName());
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedUser));
    }

    private User generateDummyUser() {
        User user = new User();
        user.setFirstName("User FirstName");
        user.setSecondName("User SecondName");
        user.setUsername("Username");

        Address address = new Address();
        address.setAddressLine1("address line 1");
        address.setAddressLine2("address line 2");
        address.setNumber("123");
        address.setCountry("USA");
        address.setPostcode("12345");
        user.setAddress(address);

        user.setEnabled(true);
        return user;
    }
}