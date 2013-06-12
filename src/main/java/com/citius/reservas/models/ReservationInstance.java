/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Entity
@Table(name = "reservation_instances")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservationInstance.findAll", 
            query = "SELECT r FROM ReservationInstance r"),
    @NamedQuery(name = "ReservationInstance.findByReservation", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + "r.reservation.id = :reservationId"),
    @NamedQuery(name = "ReservationInstance.findBetweenDates", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + "r.startTimeDate <= :endTimeDate OR r.endTimeDate >= :startTimeDate"),
    @NamedQuery(name = "ReservationInstance.findBetweenDatesByOwner", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + "(r.startTimeDate <= :endTimeDate OR r.endTimeDate >= :startTimeDate) "
            + "AND r.reservation.owner.uniqueName = :ownerUniqueName"),
    @NamedQuery(name = "ReservationInstance.findByOwner", 
            query = "SELECT r FROM ReservationInstance r WHERE r.reservation.owner.id = :ownerUniqueName "),
    @NamedQuery(name = "ReservationInstance.findAfterDateByOwner", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + "r.endTimeDate >= :startTimeDate "
            + "AND r.reservation.owner.uniqueName = :ownerUniqueName"),
    @NamedQuery(name = "ReservationInstance.findByResource", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + ":resource MEMBER OF r.reservation.resources"),
    @NamedQuery(name = "ReservationInstance.findBetweenDatesByResource", 
            query = "SELECT r FROM ReservationInstance r WHERE "
            + "(r.startTimeDate <= :endTimeDate OR r.endTimeDate >= :startTimeDate) "
            + "AND :resource MEMBER OF r.reservation.resources"),
})
public class ReservationInstance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Column(name = "start_time_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTimeDate;
    
    @NotNull
    @Column(name = "end_time_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTimeDate;
    
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reservation reservation;

    public ReservationInstance() {
    }

    public ReservationInstance(Integer id) {
        this.id = id;
    }

    public ReservationInstance(Reservation reservation, Calendar startTimeDate, Calendar endTimeDate) {
        this.reservation = reservation;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(Calendar startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public Calendar getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(Calendar endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.startTimeDate);
        hash = 37 * hash + Objects.hashCode(this.endTimeDate);
        hash = 37 * hash + Objects.hashCode(this.reservation);
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
        final ReservationInstance other = (ReservationInstance) obj;
        if (!Objects.equals(this.startTimeDate, other.startTimeDate)) {
            return false;
        }
        if (!Objects.equals(this.endTimeDate, other.endTimeDate)) {
            return false;
        }
        if (!Objects.equals(this.reservation, other.reservation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationInstance{" + "id=" + id + ", startTimeDate=" + startTimeDate + ", endTimeDate=" + endTimeDate + ", reservationId=" + reservation.getId() + '}';
    }
    
    


    
}
