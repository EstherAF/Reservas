/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Repository
public class ReservationInstanceRepositoryImpl extends GenericRepositoryImpl<ReservationInstance> implements ReservationInstanceRepository{

    private static final Logger logger = Logger.getLogger(ReservationInstanceRepositoryImpl.class);
    
    @Override
    @Transactional
    public List<ReservationInstance> findBetweenDates(String ownerUniqueName, Date startTimeDate, Date endTimeDate) {
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
    @Transactional
    public List<ReservationInstance> findByDay(String ownerUniqueName, Date day) {
        Calendar startTimeDate, endTimeDate, dayCalendar;
        dayCalendar = Calendar.getInstance();
        dayCalendar.setTime(day);
        
        startTimeDate = new GregorianCalendar(
                dayCalendar.get(Calendar.YEAR), 
                dayCalendar.get(Calendar.MONTH), 
                dayCalendar.get(Calendar.DAY_OF_MONTH), 
                0, 0, 0);
        endTimeDate = new GregorianCalendar(
                dayCalendar.get(Calendar.YEAR), 
                dayCalendar.get(Calendar.MONTH), 
                dayCalendar.get(Calendar.DAY_OF_MONTH), 
                23, 59, 59);
        
        logger.debug("ReservationInstance.findBetweenDatesByOwner:"+startTimeDate+","+endTimeDate+","+ownerUniqueName);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByOwner");
        q.setParameter("startTimeDate", startTimeDate.getTime());
        q.setParameter("endTimeDate", endTimeDate.getTime());
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    @Transactional
    public List<ReservationInstance> findByReservation(Integer reservationId) {
        logger.debug("ReservationInstance.findByReservation:"+reservationId);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findByReservation");
        q.setParameter("reservationId", reservationId);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    @Transactional
    public List<ReservationInstance> findAfterDate(String ownerUniqueName, Date startTimeDate) {
        String query = "ReservationInstance.findAfterDateByOwner";
        logger.debug(query+":"+startTimeDate+","+ownerUniqueName);
        
        Query q = this.em.createNamedQuery(query);
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }
    
    
    @Override
    @Transactional
    public Boolean isAvaliable(Resource resource, Date startTimeDate, Date endTimeDate) {
        logger.debug("Reservation.findBetweenDatesByResource:"+resource.getId()+","+startTimeDate+","+endTimeDate);
        
        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByResource");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("resource", resource);
        List<ReservationInstance> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l.isEmpty();
    }
    
}
