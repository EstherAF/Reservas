///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.citius.reservas.controllers.rest;
//
//import com.citius.reservas.business.ResourceGroupBusiness;
//import com.citius.reservas.models.Resource;
//import com.citius.reservas.models.ResourceGroup;
//import org.junit.After;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// *
// * @author Esther Álvarez Feijoo
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext-test.xml",
//    "classpath:applicationContext-security-test.xml"})
//public class ResourcesGroupsRestTest {
//    @Autowired
//    private ResourcesGroupsRest rgr;
//    @Autowired
//    private ResourcesRest rr;
//    
//    private ResourceGroup rg;
//    private Resource r;
//
//    @Test
//    public void testReadCreate() {
//        rg = rgr.create("grupo junit");
//        r = rr.create("Recurso junit", null, rg.getId());
//        
//        assertNotNull(rg.getId());
//        
//        rg = rgr.read(rg.getId());
//        assertNotNull(rg);
//        assertTrue(rg.getName().equals("grupo junit"));
//        
//        rgr.delete(rg.getId());
//        //assertFalse(rg.getResources().isEmpty());
//    }
//
//    @Test
//    public void testUpdate() {
//        rg = rgr.create("grupo junit");
//        r = rr.create("Recurso junit", null, rg.getId());
//        
//        rgr.update(rg.getId(), "nuevo");
//        
//        rg = rgr.read(rg.getId());
//        assertEquals(rg.getName(), "nuevo");
//        
//        rgr.delete(rg.getId());
//        //assertFalse(rg.getResources().isEmpty());
//    }
//
//    @Test
//    public void testDeleteOnlyGroup() {
//        rg = rgr.create("grupo junit");
//        r = rr.create("Recurso junit", null, rg.getId());
//        
//        Integer id = r.getId();
//        
//        rgr.deleteOnlyGroup(rg.getId());
//        rg = rgr.read(rg.getId());
//        assertNull(rg);
//        
//        r = rr.read(id);
//        assertEquals("grupo junit", r.getGroup().getName());
//    }
//
//    @Test
//    public void testDelete() {      
//        rg = rgr.create("grupo junit");
//        r = rr.create("Recurso junit", null, rg.getId());
//        
//        rgr.delete(rg.getId());
//        assertNull(rgr.read(rg.getId()));
//        //r = rr.read(r.getId());
//        //assertNull(r);
//    }
//}