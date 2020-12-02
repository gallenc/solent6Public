package org.solent.com504.project.impl.dao.user.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.user.springdata.UserRepository;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class UserDAOImplSpringDeleteTest {

    @Autowired
    private UserDAOImplSpring userDAOImpl;

    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    public void testDeleteById() {
        User savedResult = userDAOImpl.save(generateDummyUser());
        userDAOImpl.deleteById(savedResult.getId());
        assertNull(userDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDelete() {
        User savedResult = userDAOImpl.save(generateDummyUser());
        userDAOImpl.delete(savedResult);
        assertNull(userDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        userDAOImpl.deleteAll();
        assertTrue(repository.findAll().isEmpty());
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
