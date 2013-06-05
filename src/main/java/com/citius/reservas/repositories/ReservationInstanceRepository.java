/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.ReservationInstance;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationInstanceRepository extends GenericRepository<ReservationInstance> {
    public List<ReservationInstance> findBetweenDates(String ownerUniqueName, Calendar startTimeDate, Calendar endTimeDate);
    public List<ReservationInstance> findAfterDate(String ownerUniqueName, Calendar startTimeDate);
    public List<ReservationInstance> findByDay(String ownerUniqueName, Calendar day);
    public List<ReservationInstance> findByReservation(Integer reservationId);
    
}
