/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import com.citius.reservas.repositories.InvitationRepository;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Repository
public class InvitationRepositoryImpl extends GenericRepositoryImpl<Invitation> implements InvitationRepository{

    private static final Logger logger = Logger.getLogger(InvitationRepositoryImpl.class);
    
    @Override
    public List<Invitation> findByGuestAndState(String uniqueName, InvitationState state) {
        logger.debug("Invitation.findPendingByGuestAndState:"+uniqueName+", "+state);
        
        Query q = this.em.createNamedQuery("Invitation.findPendingByGuestAndState");
        q.setParameter("uniqueName", uniqueName);
        q.setParameter("state", state);
        List<Invitation> l = this.listQuery(q);
        
        logger.debug("Found "+l.size()+" results");
        return l;
    }

    @Override
    public Invitation find(Integer reservationId, String guestUniqueName) {
        logger.debug("Invitation.find:"+guestUniqueName+", "+reservationId);
        
        Query q = this.em.createNamedQuery("Invitation.find");
        q.setParameter("uniqueName", guestUniqueName);
        q.setParameter("reservationId", reservationId);
        Invitation i = this.singleQuery(q);
        
        logger.debug("Found "+i);
        return i;
    }
    
}
