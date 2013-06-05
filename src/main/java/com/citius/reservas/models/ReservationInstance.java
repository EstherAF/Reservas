/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Calendar;
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
    @NamedQuery(name = "ReservationInstance.findAll", query = "SELECT r FROM ReservationInstance r"),
    @NamedQuery(name = "ReservationInstance.findById", query = "SELECT r FROM ReservationInstance r WHERE r.id = :id"),
    @NamedQuery(name = "ReservationInstance.findByReservation", query = "SELECT r FROM ReservationInstance r WHERE r.reservation.id = :reservationId"),
    @NamedQuery(name = "ReservationInstance.findBetweenDates", 
        query = "SELECT r FROM ReservationInstance r WHERE r.startTimeDate <= :endTimeDate OR r.endTimeDate >= :startTimeDate"),
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservationInstance)) {
            return false;
        }
        ReservationInstance other = (ReservationInstance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.citius.reservas.models.ReservationInstance[ id=" + id + " ]";
    }
    
}
