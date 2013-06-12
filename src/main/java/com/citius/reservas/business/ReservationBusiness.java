/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationBusiness {
        
    
public Reservation createReservation(String name, String description, 
            String ownerUniqueName,
            Calendar startDate, Calendar endDate, Calendar startTime, Calendar endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException;

    public Reservation saveReservation(Integer id, String name, String description,
            Calendar startDate, Calendar endDate, Calendar startTime, Calendar endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException;
    
    public void deleteReservation(Integer id);
    
    public Reservation read(Integer reservation_id);
    
    public Boolean isOwner(Integer reservationId, String ownerUniqueName);
    
    //public List<ReservationInstance> readBetweenDates(Calendar startDate, Calendar endDate);
    
    //public List<ReservationInstance> readBetweenDates(Integer owner_id, Calendar startDate, Calendar endDate);
    
    public List<ReservationInstance> readByWeek(String ownerUniqueName, Integer week, Integer year);
    
    public List<ReservationInstance> readByMonth(String ownerUniqueName, Integer month, Integer year);
    
    public List<ReservationInstance> readByOwnerAfterDate(String ownerUniqueName, Calendar startDate);
    
    //public List<ReservationInstance> readInstances(Integer reservation_id);
    
    
}
