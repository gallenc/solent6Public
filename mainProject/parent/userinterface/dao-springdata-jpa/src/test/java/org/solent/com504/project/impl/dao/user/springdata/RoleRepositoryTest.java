package org.solent.com504.project.impl.dao.user.springdata;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role generatedRole;

    @Before
    @Transactional
    public void setUp() throws Exception {
        generatedRole = roleRepository.save(generateDummyRole());
    }

    @After
    @Transactional
    public void tearDown() throws Exception {
        roleRepository.deleteById(generatedRole.getId());
    }

    @Test
    @Transactional
    public void findByName() {
        List<Role> result = roleRepository.findByName(generatedRole.getName());
        assertFalse(result.isEmpty());
        assertTrue(result.contains(generatedRole));
    }

    private Role generateDummyRole() {
        Role role = new Role();
        role.setName("DUMMY ROLE");
        return role;
    }
}