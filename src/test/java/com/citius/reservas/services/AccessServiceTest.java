/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.services;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.models.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-security-test.xml"})
public class AccessServiceTest {

    @Autowired
    private AccessBusiness as;
    
    @Test
    public void testFindUser() {
        String name = "perico";
        User u = as.findUser(name);
        assertTrue("No es nulo",u!=null);
        assertTrue(u.getUniqueName().equals(name));
    }


}