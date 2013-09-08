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

    /*
     * Content: JSON
     * @return Lista de invitaciones con estado WAITING del usuario validado
     * @see Invitation, InvitationState
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Invitation> readPendingInvitations();

    
    /*
     * Content: JSON
     * @param userName nombre de usuario
     * @return Lista de invitaciones con estado WAITING del usuario pasado por parámetro
     * @see Invitation, InvitationState
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{uniqueName}",
            method = RequestMethod.GET)
    public List<Invitation> readPendingInvitations(
            @PathVariable(value = "uniqueName") String guestUniqueName);

    /*
     * Content: JSON
     * Description: Crea una invitación, si el usuario es administrador o el Owner de la reserva de la invitación
     * @param invitation invitación a crear
     * @return Invitation invitacion creada
     * @throws InputRequestvalidationException Error de validez de los datos de la invitación
     * @throws AccessDeniedException El usuario no es administrador ni el propietario de la reserva
     * @throws UnknownResourceException La reserva no existe
     * @see Invitation
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Invitation createInvitation(@Valid @RequestBody Invitation invitation,
            BindingResult result) throws InputRequestValidationException, UnknownResourceException;

    /*
     * Content: JSON
     * Description: Cambia el estado de una invitación del usuario validado
     * @param reservationId id de la reserva
     * @param state nuevo estado de la invitación
     * @return Invitation invitación con el estado cambiado
     * @throws InputRequestvalidationException Error de validez de los datos de la invitación
     * @throws AccessDeniedException El usuario no es ni administrador ni el propietario de la reserva
     * @throws UnknownResourceException La reserva no existe
     * @see Invitation
     */
    @ResponseBody
    @RequestMapping(value = "/{reservationId}/{state}",
            method = RequestMethod.PUT)
    public Invitation changeStateInvitation(
            @PathVariable(value = "reservationId") Integer reservationId,
            @PathVariable(value = "state") InvitationState state)
            throws UnknownResourceException;

    /*
     * Content: JSON
     * Description: Cambia le estado de una invitación
     * @param reservationId id de la reserva
     * @param state nuevo estado de la invitación
     * @param guestUniqueName UniqueName del usuario al que invitar
     * @return Invitation invitación con el estado cambiado
     * @throws UnknownResourceException La reserva o el usuario no existen
     * @see Invitation
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{reservationId}/{guestUniqueName}/{state}",
            method = RequestMethod.PUT)
    public Invitation changeStateInvitation(
            @PathVariable(value = "reservationId") Integer reservationId,
            @PathVariable(value = "uniqueName") String guestUniqueName,
            @PathVariable(value = "state") InvitationState state)
            throws UnknownResourceException;

}
