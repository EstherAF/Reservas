/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Transactional
public interface InvitationBusiness {
    
    /**
     *
     * @param uniqueName
     * @return
     */
    @Transactional(readOnly = true)
    public List<Invitation> getPendingInvitationsByGuest(String uniqueName);
    
    /**
     *
     * @param reservationId
     * @param guestUniqueName
     * @param state
     * @return
     * @throws UnknownResourceException
     */
    public Invitation changeStateInvitation(Integer reservationId, String guestUniqueName, InvitationState state) throws UnknownResourceException;
    
    /**
     *
     * @param invitation
     * @return
     * @throws UnknownResourceException
     */
    public Invitation createOrSaveInvitation(Invitation invitation) throws UnknownResourceException;
    
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
    
    /**
     *
     * @param invitation
     * @return
     */
    public Invitation save(Invitation invitation);
    
}
