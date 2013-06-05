/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.services;

import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.models.Resource;
import com.google.common.base.Strings;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Esther
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:applicationContext-security-test.xml"})
public class ResourceServiceTest {
    
    @Autowired
    private ResourceBusiness rs;
    
    public ResourceServiceTest() {
    }

    @Test
    public void testRead() {
        Resource r = rs.readAll();
        
        assertFalse("Recurso nulo",r==null);
        assertTrue("Id del padre no es 1",r.getId()==1);
    }
    
    @Test
    public void testReadById() {
        Resource r = rs.read(2);
        
        assertFalse("Recurso nulo",r==null);
        assertTrue("Id incorrecto",r.getId()==2);
    }
}