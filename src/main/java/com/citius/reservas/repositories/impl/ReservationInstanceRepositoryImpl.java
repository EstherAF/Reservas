/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Repository
public class ReservationInstanceRepositoryImpl extends GenericRepositoryImpl<ReservationInstance> implements ReservationInstanceRepository{

    private static final Logger logger = Logger.getLogger(ReservationInstanceRepositoryImpl.class);
    
    @Override
    public List<ReservationInstance> findBetweenDates(String ownerUniqueName, Calendar startTimeDate, Calendar endTimeDate) {
        logger.debug("ReservationInstance.findBetweenDatesByOwner:"+startTimeDate+","+endTimeDate);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByOwner");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public List<ReservationInstance> findByDay(String ownerUniqueName, Calendar day) {
        Calendar startTimeDate, endTimeDate;
        
        startTimeDate = new GregorianCalendar(
                day.get(Calendar.YEAR), 
                day.get(Calendar.MONTH), 
                day.get(Calendar.DAY_OF_MONTH), 
                0, 0, 0);
        endTimeDate = new GregorianCalendar(
                day.get(Calendar.YEAR), 
                day.get(Calendar.MONTH), 
                day.get(Calendar.DAY_OF_MONTH), 
                23, 59, 59);
        
        logger.debug("ReservationInstance.findBetweenDatesByOwner:"+startTimeDate+","+endTimeDate+","+ownerUniqueName);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByOwner");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public List<ReservationInstance> findByReservation(Integer reservationId) {
        logger.debug("ReservationInstance.findByReservation:"+reservationId);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findByReservation");
        q.setParameter("reservationId", reservationId);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public List<ReservationInstance> findAfterDate(String ownerUniqueName, Calendar startTimeDate) {
        String query = "ReservationInstance.findAfterDateByOwner";
        logger.debug(query+":"+startTimeDate+","+ownerUniqueName);
        
        Query q = this.em.createNamedQuery(query);
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }
    
    
    
}
