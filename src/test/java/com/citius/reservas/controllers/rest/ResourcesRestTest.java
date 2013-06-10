/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.rest;

import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml",
    "classpath:applicationContext-security-test.xml"})
public class ResourcesRestTest {
    
    @Autowired
    private ResourcesRest rr;

    @Test
    public void testRead() {
        Resource r = rr.create("name", null, 2, 1);
        
        List<ResourceGroup> l = rr.read();
        assertFalse(l.isEmpty());
        
        rr.delete(r.getId());
    }
    
    @Test
    public void testRead_Integer() {
        Resource r = rr.create("name", null, 2, 1);
        
        Resource r2 = rr.read(r.getId());
        assertEquals(r.getId(), r2.getId());
        
        rr.delete(r.getId());
    }

    @Test(expected=ConstraintViolationException.class)
    public void testCreate_illegalQuantity() {
        rr.create("name", null, -1, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreate_illegalGroup() {
        rr.create("name", null, 1, -8);
    }
    
    
    @Test(expected=ConstraintViolationException.class)
    public void testCreate_illegalName() {
        
        rr.create("namenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamename", null, 1, 1);
        
    }    

    @Test
    public void testUpdate() {
        Resource r = rr.create("name", null, 1, 1);
        Resource r2 = rr.update(r.getId(), "update", r.getDescription(), r.getQuantity(), r.getGroup().getId());
        
        assertFalse(r2.getName().equals(r.getName()));
        
        rr.delete(r.getId());
    }

}