/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.business.InvitationBusiness;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import com.citius.reservas.repositories.InvitationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Service
public class InvitationBusinessImpl implements InvitationBusiness{

    @Autowired
    private InvitationRepository ir;
    
    @Override
    public List<Invitation> getPendingInvitationsByGuest(String uniqueName) {
        List<Invitation> l = ir.findByGuestAndState(uniqueName, InvitationState.WAITING);
        return l;
    }

    @Override
    public Invitation createOrSaveInvitation(Invitation invitation) {
        return ir.save(invitation);
    }

    @Override
    public Invitation changeStateInvitation(Integer reservationId, String guestUniqueName, InvitationState state) throws UnknownResourceException{
        Invitation invitation = this.ir.find(reservationId, guestUniqueName);
        if(invitation==null)
            throw new UnknownResourceException();
        
        invitation.setState(state);
        ir.save(invitation);
        
        return invitation;
    }
    
}
