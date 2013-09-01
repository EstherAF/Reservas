/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import com.citius.reservas.controllers.jsonMapper.InvitationJsonSerializer;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Entity
@IdClass(InvitationId.class)
@Table(name = "invitations")
@NamedQueries(value = {
    @NamedQuery(name = "Invitation.findPendingByGuestAndState", 
        query = "SELECT i FROM Invitation i "
        + "WHERE i.guest.uniqueName = :uniqueName "
        + "AND i.state = :state"),
    @NamedQuery(name = "Invitation.find", 
        query = "SELECT i FROM Invitation i "
        + "WHERE i.reservation.id = :reservationId "
        + "AND i.guest.uniqueName = :uniqueName")
})
@JsonSerialize(using = InvitationJsonSerializer.class)
public class Invitation implements Serializable {
    
    @Id
    @NotNull(message = "error.form.invitations.user.required")
    @Valid
    @ManyToOne(cascade = {})
    @JoinColumn(name = "\"user\"")
    private User guest;
    
    @Enumerated(EnumType.STRING)
    private InvitationState state;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "reservation_id",
            referencedColumnName = "id")
    private Reservation reservation;

    public Invitation(User guest, InvitationState state, Reservation reservation) {
        this.guest = guest;
        this.state = state;
        this.reservation = reservation;
    }
    
    public Invitation(String guest, InvitationState state, Integer reservationId) {
        this.guest = new User();
        this.guest.setUniqueName(guest);
        this.reservation = new Reservation();
        this.reservation.setId(reservationId);
        this.state = state;
    }

    public Invitation() {
    }
    
    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public InvitationState getState() {
        return state;
    }

    public void setState(InvitationState state) {
        this.state = state;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.guest!=null ? this.guest.hashCode() : 0);
        hash = 29 * hash + (this.reservation!=null ? Objects.hashCode(this.reservation.getId()) : 0);
        hash = 29 * hash + (this.state!=null ? this.state.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invitation other = (Invitation) obj;
        if (!Objects.equals(this.guest, other.guest)) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        if(this.reservation!=null && this.reservation!=null){
            if (!Objects.equals(this.reservation.getId(), other.reservation.getId())) {
                return false;
            }
        }else if(!this.reservation.equals(other.reservation))
            return false;
        
        return true;
    }
    
}
