/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.customModel;

import com.citius.reservas.models.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ReservationInstanceCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String startTime;
    private String endTime;
    private String date;
    private ReservationCustom reservation;

    public ReservationInstanceCustom() {
    }

    public ReservationInstanceCustom(Integer id) {
        this.id = id;
    }

    public ReservationInstanceCustom(Integer id, ReservationCustom reservation, String startTime, String endTime, String date) {
        this.id = id;
        this.reservation = reservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
    
    public ReservationInstanceCustom(ReservationInstance r) {
        this.id = r.getId();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        this.startTime = format.format(r.getStartTimeDate());
        this.endTime = format.format(r.getEndTimeDate());
        this.reservation = new ReservationCustom(r.getReservation());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ReservationCustom getReservation() {
        return reservation;
    }

    public void setReservation(ReservationCustom reservation) {
        this.reservation = reservation;
    } 

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.startTime);
        hash = 37 * hash + Objects.hashCode(this.endTime);
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
        final ReservationInstanceCustom other = (ReservationInstanceCustom) obj;
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationInstance{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", reservationId=" + reservation.getId() + '}';
    }
    
    


    
}
