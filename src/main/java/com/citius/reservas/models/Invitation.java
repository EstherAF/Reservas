/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Entity
@IdClass(InvitationId.class)
@Table(name = "invitations")
public class Invitation implements Serializable {
    
    @Id
    @ManyToOne()
    @JoinColumn(name = "user",
            referencedColumnName = "unique_name")
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
        hash = 29 * hash + Objects.hashCode(this.guest);
        hash = 29 * hash + Objects.hashCode(this.reservation);
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
        if (!Objects.equals(this.reservation, other.reservation)) {
            return false;
        }
        return true;
    }
    
}
