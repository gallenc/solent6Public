package org.solent.com504.project.impl.dao.user.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.model.user.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class RoleDAOImplSpringTest {

    @Autowired
    private RoleDAOImplSpring roleDAOImpl;

    private Role generatedRole;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedRole = roleDAOImpl.save(generateDummyRole());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        roleDAOImpl.deleteById(generatedRole.getId());
    }

    @Test
    @Transactional
    public void testFindById() {
        Role result = roleDAOImpl.findById(generatedRole.getId());
        assertNotNull(result);
        assertEquals(generatedRole.getId(), result.getId());
    }

    @Test
    @Transactional
    public void testSave() {
        Role savedResult = roleDAOImpl.save(generateDummyRole());
        assertNotNull(savedResult);
        assertNotNull(savedResult.getId());
    }

    @Test
    @Transactional
    public void testFindAll() {
        List<Role> results = roleDAOImpl.findAll();
        assertFalse(results.isEmpty());
        assertTrue(results.contains(generatedRole));
    }

    @Test
    @Transactional
    public void testFindByRoleName() {
        Role result = roleDAOImpl.findByRoleName(generatedRole.getName());
        assertNotNull(result);
        assertEquals(generatedRole.getName(), result.getName());
    }

    private Role generateDummyRole() {
        Role role = new Role();
        role.setName("ADMIN");
        return role;
    }
}