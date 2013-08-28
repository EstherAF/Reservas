/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.impl;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.business.InvitationBusiness;
import com.citius.reservas.controllers.InvitationController;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 *
 * @author Esther
 */
@Controller
public class InvitationControllerImpl implements InvitationController {

    @Autowired
    private InvitationBusiness ib;
    @Autowired
    private AccessBusiness access;

    @Override
    public List<Invitation> readPendingInvitations() {
        //String loggedName = access.getLoggedUser();
        String loggedName = "perico";
        return ib.getPendingInvitationsByGuest(loggedName);
    }

    //Role Admin
    @Override
    public List<Invitation> readPendingInvitations(String guestUniqueName) {
        //String loggedName = access.getLoggedUser();
        String loggedName = "perico";

        return ib.getPendingInvitationsByGuest(guestUniqueName);

    }

    @Override
    public Invitation createInvitation(Invitation invitation, BindingResult result)
            throws InputRequestValidationException {

        String loggedName = "perico";

        if (!invitation.getReservation().getOwner().getUniqueName().equals(loggedName) && !access.isAdmin(loggedName)) {
            throw new AccessDeniedException("");
        }

        if (!result.getAllErrors().isEmpty()) {
            throw new InputRequestValidationException(result.getAllErrors());
        }

        return ib.createOrSaveInvitation(invitation);
    }

    //Admin
    @Override
    public Invitation changeStateInvitation(Integer reservationId, String guestUniqueName, InvitationState state)
            throws UnknownResourceException {
        Invitation invitation = ib.changeStateInvitation(reservationId, guestUniqueName, state);
        return invitation;
    }

    @Override
    public Invitation changeStateInvitation(Integer reservationId, InvitationState state)
            throws UnknownResourceException {
        //String loggedName = access.getLoggedUser();
        String loggedName = "perico";

        Invitation invitation = ib.changeStateInvitation(reservationId, loggedName, state);

        return invitation;
    }

    @Override
    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException {
        throw new NoSuchRequestHandlingMethodException(request);
    }
}
