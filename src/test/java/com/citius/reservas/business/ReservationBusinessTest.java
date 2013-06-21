///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.citius.reservas.business;
//
//import com.citius.reservas.exceptions.NotAvaliableException;
//import com.citius.reservas.models.Reservation;
//import com.citius.reservas.models.DayOfWeek;
//import com.citius.reservas.repositories.UserRepository;
//import com.citius.reservas.models.RepetitionType;
//import com.citius.reservas.models.Resource;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.List;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.BeforeClass;
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
//public class ReservationBusinessTest {
//
//    @Autowired
//    private ReservationBusiness rs;
//    @Autowired
//    private ResourceBusiness resourceB;
//    @Autowired
//    private static UserRepository ur;
//    
//    private static Calendar sD, sT, eD, eT;
//    
//    private static List<Integer> days = new ArrayList<>();
//
//    @BeforeClass
//    public static void initilization() {
//        
//
//        sD = new GregorianCalendar(2013, 5, 25);
//
//        eD = new GregorianCalendar(2013, 5, 27);
//
//        sT = Calendar.getInstance();
//        sT.set(Calendar.HOUR_OF_DAY, 15);
//        sT.set(Calendar.MINUTE, 00);
//
//        eT = Calendar.getInstance();
//        eT.set(Calendar.HOUR_OF_DAY, 15);
//        eT.set(Calendar.MINUTE, 30);
//        
//        days.add(DayOfWeek.MONDAY.getNumber());
//        days.add(DayOfWeek.TUESDAY.getNumber());
//        
//        
//    }
//    
//    @Test
//    public void createOnceReservation() throws NotAvaliableException {
//        Reservation r;
//        
//        List<Integer> l = new ArrayList<>();
//        //Resource a = resourceB.create("test", 1, null);
//        //Resource b = resourceB.create("test2", 1, null);
//        Resource a = resourceB.read(260);
//        Resource b = resourceB.read(261);
//        Integer id=0;
//        l.add(a.getId());
//        l.add(b.getId());
//        
//        r = rs.createReservation("Nombre", "Descripcion", "perico", sD.getTime(), eD.getTime(), sT.getTime(), eT.getTime(), RepetitionType.ONCE.toString(), 1, days,l);
//        id=r.getId();
//        assertNotNull("Id nulo",r.getId());
//        assertNotNull("No se han creado instancias",r.getInstances());
//        assertFalse("No se han creado instancias",r.getInstances().isEmpty());
//        
//        r = rs.read(r.getId());
//        assertNotNull("There is no resources:NULL",r.getResources());
//        assertFalse("There is no resources:EMPTY",r.getResources().isEmpty());
//        
//        Boolean exception = false;
//        
//        try{
//            rs.createReservation("Nombre2", "Descripcion", "perico", sD.getTime(), eD.getTime(), sT.getTime(), eT.getTime(), RepetitionType.ONCE.toString(), 1, days,l);
//        }catch(NotAvaliableException e){
//            exception=true;
//        }
//        
//        assertTrue("Exception wasn't thrown",exception);
//        
//        rs.deleteReservation(id);
//        //resourceB.delete(a.getId());
//        //resourceB.delete(b.getId());
//        
//    }
//
////    @Test
////    public void createWeeklyReservation() throws NotAvaliableException {
////        Reservation r;
////        
////                List<Integer> l = new ArrayList<>();
////        Resource a = resourceB.create("test", 1, null);
////        Resource b = resourceB.create("test2", 1, null);
////        l.add(a.getId());
////        l.add(b.getId());
////        
////        r = rs.createReservation("Nombre", "Descripcion", "perico", sD, eD, sT, eT, RepetitionType.WEEKLY.toString(), 1, days,l);
////        
////        assertNotNull("Id nulo",r.getId());
////        assertNotNull("No se han creado instancias",r.getInstances());
////        assertFalse("No se han creado instancias",r.getInstances().isEmpty());
////    }
//    
////    @Test
////    public void createMonthlyReservation() {
////        Reservation r;
////        
////        eD.add(Calendar.MONTH, 2);
////        
////        r = rs.createReservation("Nombre2", "Descripcion", "perico", sD, eD, sT, eT, RepetitionType.MONTHLY.toString(), 1, null);
////        
////        assertNotNull("Id nulo",r.getId());
////        assertNotNull("No se han creado instancias",r.getInstances());
////        assertFalse("No se han creado instancias",r.getInstances().isEmpty());
////    }
////    
////    @Test
////    public void createMonthlyRelativeReservation() {
////        Reservation r;
////        
////        eD.add(Calendar.MONTH, 2);
////        
////        r = rs.createReservation("Nombre3", "Descripcion", "perico", sD, eD, sT, eT, RepetitionType.MONTHLY_RELATIVE.toString(), 1, null);
////        
////        assertNotNull("Id nulo",r.getId());
////        assertNotNull("No se han creado instancias",r.getInstances());
////        assertFalse("No se han creado instancias",r.getInstances().isEmpty());
////    }
//}