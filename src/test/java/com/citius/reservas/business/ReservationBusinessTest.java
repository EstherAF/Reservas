/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml",
    "classpath:applicationContext-security-test.xml"})
public class ReservationBusinessTest{

    @Autowired
    private ReservationBusiness rs;
    @Autowired
    private ResourceBusiness resourceB;
    @Autowired
    private AccessBusiness access;
    
    @Autowired
    private ResourceGroupBusiness resourceGroupB;
    private static Calendar start, end, endRepetition;
    private User owner;
    private Set<Resource> resources;
    private static Set<DayOfWeek> days = new LinkedHashSet<>();
    private Set<Invitation> invitations;

    @BeforeClass
    public static void initilization() {
    }

    @Before
    public void initTest() {
        owner = this.access.findUser("perico");
        resources = new LinkedHashSet<>();

        if (resourceGroupB.read(1) == null) {
            resourceGroupB.create(new ResourceGroup("default"));
        }

        Resource resource = resourceB.readByName("test");
        if (resource == null) {
            
            resources.add(
                resourceB.createOrSave(
                    new Resource("test", new ResourceGroup(1))
                )
            );
            resources.add(
                resourceB.createOrSave(
                    new Resource("test2", new ResourceGroup(1))
                )
            );
        } else {
            resources.add(resource);
            resources.add(resourceB.readByName("test2"));
        }
        invitations = new LinkedHashSet<>();
        User guest1 = new User("paquito", null, null);
        invitations.add(new Invitation(guest1, InvitationState.WAITING, null));
    }

    @Test
    public void createOnceReservation() throws NotAvaliableException, NotPossibleInstancesException, UnknownResourceException {
        Reservation r;

        start = new GregorianCalendar(2013, 10, 22, 15, 00);
        end = new GregorianCalendar(2013, 10, 24, 8, 00);
        endRepetition = new GregorianCalendar(2013, 8, 30);

        Repetition repetition = new Repetition(RepetitionType.ONCE, 1, days, endRepetition.getTime());
        r = new Reservation("name2", "description", owner, start.getTime(), end.getTime(), repetition, resources);
        
        r.setInvitations(invitations);

        r = rs.saveReservation(r, null);

        assertNotNull("Id nulo", r.getId());
        assertNotNull("No se han creado instancias", r.getInstances());
        assertFalse("No se han creado instancias", r.getInstances().isEmpty());
        assertTrue("El número de instancias está mal", r.getInstances().size() == 1);
        
        ReservationInstance i=null;
        for(ReservationInstance i2: r.getInstances()){
            if(i2.getReservation().getName().equals("name2"))
                i=i2;
            else 
                i=null;
        }
        
        assertNotNull(i);

        assertEquals(end.getTime(), i.getEndTimeDate());
        assertEquals(start.getTime(), i.getStartTimeDate());

        //rs.deleteReservation(r.getId());

    }
//
//    @Test
//    public void createWeeklyReservation() throws NotAvaliableException, NotPossibleInstancesException, UnknownResourceException {
//        Reservation r;
//
//        start = new GregorianCalendar(2013, 10, 20, 15, 00);
//        end = new GregorianCalendar(2013, 10, 23, 8, 00);
//        endRepetition = new GregorianCalendar(2013, 6, 30);
//
//        days.add(DayOfWeek.MONDAY);
//        days.add(DayOfWeek.TUESDAY);
//
//        Repetition repetition = new Repetition(RepetitionType.WEEKLY, 1, days, endRepetition.getTime());
//        r = new Reservation("name3", "description", owner, start.getTime(), end.getTime(), repetition, resources);
//
//        r = rs.saveReservation(r, null);
//
//        assertNotNull("Id nulo", r.getId());
//        assertNotNull("No se han creado instancias", r.getInstances());
//        assertFalse("No se han creado instancias", r.getInstances().isEmpty());
//        assertTrue("Las instancias se han generado mal:" + r.getInstances(), r.getInstances().size() == 11);
//
//        //rs.deleteReservation(r.getId());
//    }
//
//    @Test
//    public void createMonthlyReservation() throws NotAvaliableException, NotPossibleInstancesException, UnknownResourceException {
//        Reservation r;
//        
//        start = new GregorianCalendar(2013, 10, 5, 15, 00);
//        end = new GregorianCalendar(2013, 10, 6, 8, 00);
//        endRepetition = new GregorianCalendar(2013, 9, 30);
//
//        Repetition repetition = new Repetition(RepetitionType.MONTHLY, 1, days, endRepetition.getTime());
//        r = new Reservation("name4", "description", owner, start.getTime(), end.getTime(), repetition, resources);
//
//        r = rs.saveReservation(r,null);
//
//        assertNotNull("Id nulo", r.getId());
//        assertNotNull("No se han creado instancias", r.getInstances());
//        assertFalse("No se han creado instancias", r.getInstances().isEmpty());
//        assertTrue("Las instancias se han generado mal:" + r.getInstances(), r.getInstances().size() == 5);
//
//        //rs.deleteReservation(r.getId());
//    }
//    
//    @Test
//    public void createMonthlyRelativeReservation() throws NotAvaliableException, NotPossibleInstancesException, UnknownResourceException {
//        Reservation r;
//        
//        start = new GregorianCalendar(2012, 10, 3, 15, 00);
//        end = new GregorianCalendar(2012, 10, 4, 8, 00);
//        endRepetition = new GregorianCalendar(2012, 12, 10);
//
//        Repetition repetition = new Repetition(RepetitionType.MONTHLY_RELATIVE, 1, days, endRepetition.getTime());
//        r = new Reservation("name5", "description", owner, start.getTime(), end.getTime(), repetition, resources);
//
//        r = rs.saveReservation(r,null);
//
//        assertNotNull("Id nulo", r.getId());
//        assertNotNull("No se han creado instancias", r.getInstances());
//        assertFalse("No se han creado instancias", r.getInstances().isEmpty());
//        assertTrue("Las instancias se han generado mal:" + r.getInstances(), r.getInstances().size() == 5);
//        
//        //rs.deleteReservation(r.getId());
//    }
}
