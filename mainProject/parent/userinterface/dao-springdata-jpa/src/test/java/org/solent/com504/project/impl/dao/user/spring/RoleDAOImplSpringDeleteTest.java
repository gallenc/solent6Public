package org.solent.com504.project.impl.dao.user.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.project.impl.dao.user.springdata.RoleRepository;
import org.solent.com504.project.model.user.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class RoleDAOImplSpringDeleteTest {

    @Autowired
    private RoleDAOImplSpring roleDAOImpl;

    @Autowired
    private RoleRepository repository;

    @Test
    @Transactional
    public void testDeleteById() {
        Role savedResult = roleDAOImpl.save(generateDummyRole());
        roleDAOImpl.deleteById(savedResult.getId());
        assertNull(roleDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDelete() {
        Role savedResult = roleDAOImpl.save(generateDummyRole());
        roleDAOImpl.delete(savedResult);
        assertNull(roleDAOImpl.findById(savedResult.getId()));
    }

    @Test
    @Transactional
    public void testDeleteAll() {
        roleDAOImpl.deleteAll();
        assertTrue(repository.findAll().isEmpty());
    }

    private Role generateDummyRole() {
        Role role = new Role();
        role.setName("ADMIN");
        return role;
    }

}
