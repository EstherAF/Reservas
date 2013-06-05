/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Role;
import com.citius.reservas.models.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author esther
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml",
    "classpath:applicationContext-security-test.xml"})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository rr;
    
    @Test
    @Transactional
    public void testCreateUser() {

        String name = "Perico Palotes";
        List<Role> l = new ArrayList<>();
        l.add(rr.find(2));
        User u = new User("Prueba", "Full name", "email@gmail.com", l);
        
        u = userRepository.create(u);

        assertNotNull("Id nulo", u.getId());
    }
    
    @Test
    public void testFindByName() {

        String name = "Perico Palotes";

        User result = userRepository.findByFullName(name).get(0);

        assertEquals(name, result.getFullName());
    }

    @Test
    public void testFindByEmail() {
        String email = "periquito@gmail.com";

        User result = userRepository.findByEmail(email).get(0);

        assertEquals(email, result.getEmail());
    }

    @Test
    public void testFindAll() {
        List result = userRepository.findAll();

        assertTrue(result.size() > 0);
    }
}