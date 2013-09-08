/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.business.InvitationBusiness;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.User;
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
    
    @Autowired
    private ReservationBusiness reservationBusiness;
    
    @Autowired
    private AccessBusiness accessBusiness;
    
    @Override
    public List<Invitation> getPendingInvitationsByGuest(String uniqueName) {
        List<Invitation> l = ir.findByGuestAndState(uniqueName, InvitationState.WAITING);
        return l;
    }

    @Override
    public Invitation createOrSaveInvitation(Invitation invitation) throws UnknownResourceException {
        Reservation r = reservationBusiness.read(invitation.getReservation().getId());
        User u = accessBusiness.findUserFromDB(invitation.getGuest().getUniqueName());
        
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

    @Override
    public List<Invitation> findByGuestAndState(String uniqueName, InvitationState state) {
        return this.ir.findByGuestAndState(uniqueName, state);
    }

    @Override
    public Invitation find(Integer reservationId, String guestUniqueName) {
        return this.ir.find(reservationId, guestUniqueName);
    }

    @Override
    public Invitation save(Invitation invitation) {
        return this.ir.save(invitation);
    }
    
}
