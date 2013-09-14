/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface InvitationRepository extends GenericRepository<Invitation>{
    
    /**
     *
     * @param uniqueName
     * @param state
     * @return
     */
    public List<Invitation> findByGuestAndState(String uniqueName, InvitationState state);
    
    /**
     *
     * @param reservationId
     * @param guestUniqueName
     * @return
     */
    public Invitation find(Integer reservationId, String guestUniqueName);
    
}
