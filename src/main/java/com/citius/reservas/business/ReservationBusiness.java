/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationBusiness {
        
    
    public Reservation createReservation(Reservation reservation) throws NotAvaliableException;
    
    public Reservation createReservation(String name, String description, 
            String ownerUniqueName,
            Date startDate, Date endDate, Date startTime, Date endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException;

    public Reservation saveReservation(Integer id, String name, String description,
            Date startDate, Date endDate, Date startTime, Date endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException;
    
    public void deleteReservation(Integer id);
    
    public Reservation read(Integer reservation_id);
    
    public Boolean isOwner(Integer reservationId, String ownerUniqueName);
    
    //public List<ReservationInstance> readBetweenDates(Date startDate, Date endDate);
    
    //public List<ReservationInstance> readBetweenDates(Integer owner_id, Date startDate, Date endDate);
    
    public List<ReservationInstance> readAll();
    
    public List<ReservationInstance> readByWeek(String ownerUniqueName, Integer week, Integer year);
    
    public List<ReservationInstance> readByMonth(String ownerUniqueName, Integer month, Integer year);
    
    public List<ReservationInstance> readByOwnerAfterDate(String ownerUniqueName, Date startDate);
    
    //public List<ReservationInstance> readInstances(Integer reservation_id);
    
    
}
