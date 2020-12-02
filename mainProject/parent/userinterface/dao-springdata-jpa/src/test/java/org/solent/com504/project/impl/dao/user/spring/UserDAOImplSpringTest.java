package org.solent.com504.project.impl.dao.user.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.model.party.dto.Address;
import org.solent.com504.project.model.user.dto.Role;
import org.solent.com504.project.model.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class UserDAOImplSpringTest {

    @Autowired
    private UserDAOImplSpring userDAOImpl;

    @Autowired
    private RoleRepository roleRepository;

    private User generatedUser;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedUser = userDAOImpl.save(generateDummyUser());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        userDAOImpl.deleteById(generatedUser.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        User result = userDAOImpl.findById(generatedUser.getId());
        assertNotNull(result);
        assertEquals(generatedUser.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testSave() {
        User savedResult = userDAOImpl.save(generateDummyUser());
        assertNotNull(savedResult);
        assertNotNull(savedResult.getId());

        userDAOImpl.deleteById(savedResult.getId()); // remove generated user
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<User> results = userDAOImpl.findAll();
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedUser));
    }

    @Test
    @Transactional
    public void testFindByRoleName() {
        Role role = new Role();
        role.setName("ADMIN");
        Set<User> users = new HashSet<>();
        users.add(generatedUser);
        role.setUsers(users);
        roleRepository.save(role);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        generatedUser.setRoles(roles);

        List<User> result = userDAOImpl.findByRoleName("ADMIN");
        assertFalse(result.isEmpty());
        assertTrue(result.contains(generatedUser));

        // clean
        roleRepository.delete(role);
    }

    @Test
    @Transactional
    public void testFindByNames() {
        List<User> result = userDAOImpl.findByNames(generatedUser.getFirstName(), generatedUser.getSecondName());
        assertFalse(result.isEmpty());
        assertTrue(result.contains(generatedUser));
    }

    @Test
    @Transactional
    public void testFindByUsername() {
        User result = userDAOImpl.findByUsername(generatedUser.getUsername());
        assertNotNull(result);
        assertEquals(generatedUser.getUsername(), result.getUsername());
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