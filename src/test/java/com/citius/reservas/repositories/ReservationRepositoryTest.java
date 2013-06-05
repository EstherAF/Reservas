/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.types.RepetitionType;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml",
    "classpath:applicationContext-security-test.xml"})
public class ReservationRepositoryTest {
    
    @Autowired
    private ReservationRepository rr;
    
    @Autowired
    private ReservationInstanceRepository rir;
    
    @Autowired
    private ReservationBusiness rb;
    
    @Autowired
    private UserRepository ur;
    
    private Reservation r;
    private Repetition rep;

    @Before
    @Transactional
    public void initialize() {
        
        Calendar sT = new GregorianCalendar();
        Calendar eT = new GregorianCalendar();
        sT.set(Calendar.HOUR_OF_DAY, 10);
        eT.set(Calendar.HOUR_OF_DAY, 15);
        sT.set(Calendar.MINUTE, 00);
        eT.set(Calendar.MINUTE, 00);
        
        r = rb.createReservation("Nombre prueba", "Descripción", "perico", 
                new GregorianCalendar(2013, 05, 01), 
                new GregorianCalendar(2013, 05, 01),
                sT, eT, RepetitionType.ONCE.toString(), 0, null);
    }

    @Test
    public void testCreate() {
        assertNotNull("Id de la reserva creada es nulo", r.getId());
    }

    @Test
    public void testFindById() {
        Reservation res = rr.find(r.getId());

        assertTrue("No encontrado", res != null);
        assertTrue("Propietario incorrecto", res.getOwner().equals(r.getOwner()));
    }

    @Test
    public void testFindByName() {
        List<Reservation> l = rr.findByName(r.getName());

        assertTrue("Lista vacía", !l.isEmpty());
        assertTrue("Propietario incorrecto", l.get(0).getOwner().equals(r.getOwner()));
    }

    @Test
    public void testFindByOwner() {
        List<Reservation> l = rr.findByOwner(r.getOwner());

        assertTrue("Lista vacía", l.size() > 0);
        assertTrue("Propietario incorrecto. Encontrado "+r.getOwner()
                +". Necesitado "+l.get(0).getOwner(), l.get(0).getOwner().equals(r.getOwner()));
    }

    @Test
    public void testFindBetweenDates() {
        Calendar startDate = new GregorianCalendar(2013, 4, 10);
        Calendar endDate = new GregorianCalendar(2013, 6, 5);

        List<Reservation> l = rr.findBetweenDates(startDate, endDate);

        assertTrue("Lista vacía", l.size() > 0);
        assertTrue("Fechas incorrectas", l.get(0).getStartDate().before(endDate)
                || l.get(0).getEndDate().after(startDate));
    }
}