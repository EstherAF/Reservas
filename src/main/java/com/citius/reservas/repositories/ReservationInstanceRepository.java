/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationInstanceRepository extends GenericRepository<ReservationInstance> {
    public List<ReservationInstance> findBetweenDates(String ownerUniqueName, Date startTimeDate, Date endTimeDate);
    public List<ReservationInstance> findAfterDate(String ownerUniqueName, Date startTimeDate);
    public List<ReservationInstance> findByDay(String ownerUniqueName, Date day);
    public List<ReservationInstance> findByReservation(Integer reservationId);
    public Boolean isAvaliable(Resource resource, Date startTimeDate, Date endTimeDate);
    
}
