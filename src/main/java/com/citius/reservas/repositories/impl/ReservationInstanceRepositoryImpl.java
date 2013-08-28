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

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Repository
public class ReservationInstanceRepositoryImpl extends GenericRepositoryImpl<ReservationInstance> implements ReservationInstanceRepository {

    private static final Logger logger = Logger.getLogger(ReservationInstanceRepositoryImpl.class);

    @Override
    public List<ReservationInstance> findAllBetweenDates(Date startTimeDate, Date endTimeDate) {
        logger.debug("ReservationInstance.findAllBetweenDates:" + startTimeDate + "," + endTimeDate);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDates");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;
    }

    @Override
    public List<ReservationInstance> findAllBetweenDatesByResource(
            Date startTimeDate, Date endTimeDate, Integer resourceId) {
        logger.debug("ReservationInstance.findAllBetweenDatesByResource:" + startTimeDate + "," + endTimeDate + "," + resourceId);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByResource");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("resourceId", resourceId);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;
    }

    @Override
    public List<ReservationInstance> findAllBetweenDatesByResourceGroup(
            Date startTimeDate, Date endTimeDate, Integer resourceGroupId) {

        logger.debug("ReservationInstance.findBetweenDatesByResourceGroup:" + startTimeDate + "," + endTimeDate+ "," + resourceGroupId);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByResourceGroup");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("resourceGroupId", resourceGroupId);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;

    }

    @Override
    public List<ReservationInstance> findBetweenDatesByUser(String userUniqueName, Date startTimeDate, Date endTimeDate) {
        logger.debug("ReservationInstance.findBetweenDatesByUser:" + startTimeDate + "," + endTimeDate);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByUser");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("userUniqueName", userUniqueName);
        q.setParameter("userUniqueName2", userUniqueName);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;
    }

    @Override
    public List<ReservationInstance> findByDayByUser(String ownerUniqueName, Date day) {
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

        logger.debug("ReservationInstance.findBetweenDatesByOwner:" + startTimeDate + "," + endTimeDate + "," + ownerUniqueName);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByOwner");
        q.setParameter("startTimeDate", startTimeDate.getTime());
        q.setParameter("endTimeDate", endTimeDate.getTime());
        q.setParameter("ownerUniqueName", ownerUniqueName);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;
    }

    @Override
    public List<ReservationInstance> findByReservation(Integer reservationId) {
        logger.debug("ReservationInstance.findByReservation:" + reservationId);

        Query q = this.em.createNamedQuery("ReservationInstance.findByReservation");
        q.setParameter("reservationId", reservationId);
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l;
    }

    @Override
    public Boolean isAvaliable(Resource resource, Date startTimeDate, Date endTimeDate) {
        logger.debug("ReservationInstance.findBetweenDatesByResource:" + resource.getId() + "," + startTimeDate + "," + endTimeDate);

        Query q = this.em.createNamedQuery("ReservationInstance.findBetweenDatesByResource");
        q.setParameter("startTimeDate", startTimeDate);
        q.setParameter("endTimeDate", endTimeDate);
        q.setParameter("resourceId", resource.getId());
        List<ReservationInstance> l = this.listQuery(q);

        logger.debug("Found " + l.size() + " results");
        return l.isEmpty();
    }
}
