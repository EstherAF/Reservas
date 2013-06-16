/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Embeddable
public class EventTimeDate implements Serializable{
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "start_time")
    private Date startTime;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "end_time")
    private Date endTime;

    public EventTimeDate() {
    }

    public EventTimeDate(Date startDate, Date endDate, Date startTime, Date endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Calendar getStartDateAsCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        return c;
    }

    public Calendar getEndDateAsCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        return c;
    }

    public Calendar getStartTimeAsCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        return c;
    }

    public Calendar getEndTimeAsCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(endTime);
        return c;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.startDate);
        hash = 79 * hash + Objects.hashCode(this.startTime);
        hash = 79 * hash + Objects.hashCode(this.endTime);
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
        final EventTimeDate other = (EventTimeDate) obj;
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EventTimeDate{" + "startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
    
    
}
