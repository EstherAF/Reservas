///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.citius.reservas.repositories.impl;
//
//import com.citius.reservas.model.User;
//import com.citius.reservas.repositories.UserRepository;
//import java.util.List;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// *
// * @author esther
// */
////@ContextConfiguration
////@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
//public class UserRepositoryImplTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
////    public UserRepositoryImplTest() {
////            this.userRepository = new UserRepositoryImpl();
////    }
////    
////    @BeforeClass
////    public static void setUpClass() {
////    }
////    
////    @AfterClass
////    public static void tearDownClass() {
////    }
////    
////    @Before
////    public void setUp() {
////        this.userRepository = new UserRepositoryImpl();
////    }
////    
////    @After
////    public void tearDown() {
////    }
//    
//    @Test
//    public void testInjection() {
//                assertNotNull(this.userRepository);
//    }
//    
//    @Test
//    public void testFindByName() {
//
//        String name = "Perico Palotes";
//
//        User result = userRepository.findByName(name);
//
//        assertEquals(name, result.getName());
//    }
//
//    @Test
//    public void testFindByEmail() {
//        String email = "periquito@gmail.com";
//
//        List<User> result = userRepository.findByEmail(email);
//
//        assertEquals(email, result.get(0).getEmail());
//    }
//
//    @Test
//    public void testFindAll() {
//        List result = userRepository.findAll();
//
//        assertTrue(result.size() > 0);
//    }
//}