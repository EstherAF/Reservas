/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.ReservationRepository;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Repository
public class ReservationRepositoryImpl extends GenericRepositoryImpl<Reservation> implements ReservationRepository{
    
    private static final Logger logger = Logger.getLogger(ReservationRepositoryImpl.class);

    @Override
    public List<Reservation> findByName(String name) {
        logger.debug("Reservation.findByName:"+name);
        
        Query q = this.em.createNamedQuery("Reservation.findByName");
        q.setParameter("name", name);
        List<Reservation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public List<Reservation> findByOwner(String unique_name) {
        logger.debug("Reservation.findByOwner:"+unique_name);
        
        Query q = this.em.createNamedQuery("Reservation.findByOwner");
        q.setParameter("ownerUniqueName", unique_name);
        List<Reservation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public List<Reservation> findBetweenDates(Calendar startDate, Calendar endDate) {
        logger.debug("Reservation.findBetweenDates:"+startDate+","+endDate);
        
        Query q = this.em.createNamedQuery("Reservation.findBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        List<Reservation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        
        return l;
    }

    @Override
    public List<Reservation> findByResource(Resource resource) {
        logger.debug("Reservation.findByResource:"+resource);
        
        Query q = this.em.createNamedQuery("Reservation.findByResource");
        q.setParameter("resource", resource);
        List<Reservation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        
        return l;
    }

    @Override
    public List<Reservation> findByResourceGroup(ResourceGroup group) {
        logger.debug("Reservation.findByResourceGroup:"+group);
        
        Query q = this.em.createNamedQuery("Reservation.findByResourcegroup");
        q.setParameter("resourceGroup", group);
        List<Reservation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        
        return l;
    }

    
}
