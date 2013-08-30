/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Invitation;
import com.citius.reservas.models.InvitationState;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@RequestMapping(value = "/invitations", produces = "application/json")
@Secured("IS_AUTHENTICATED_FULLY")
public interface InvitationController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Invitation> readPendingInvitations();

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{uniqueName}",
            method = RequestMethod.GET)
    public List<Invitation> readPendingInvitations(
            @PathVariable(value = "uniqueName") String guestUniqueName);

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Invitation createInvitation(@Valid @RequestBody Invitation invitation,
            BindingResult result) throws InputRequestValidationException;

    @ResponseBody
    @RequestMapping(value = "/{reservationId}/{state}",
            method = RequestMethod.PUT)
    public Invitation changeStateInvitation(
            @PathVariable(value = "reservationId") Integer reservationId,
            @PathVariable(value = "state") InvitationState state)
            throws UnknownResourceException;

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{reservationId}/{guestUniqueName}/{state}",
            method = RequestMethod.PUT)
    public Invitation changeStateInvitation(
            @PathVariable(value = "reservationId") Integer reservationId,
            @PathVariable(value = "uniqueName") String guestUniqueName,
            @PathVariable(value = "state") InvitationState state)
            throws UnknownResourceException;

//    /*Exceptions*/
//    @RequestMapping(value = "/**",
//            method = RequestMethod.GET)
//    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException;
}
