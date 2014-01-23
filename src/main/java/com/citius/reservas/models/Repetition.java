/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Embeddable
public class Repetition implements Serializable {

    @NotNull(message = "error.form.reservation.repetition.type.required")
    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_type")
    private RepetitionType type;
    @Min(value = 1, message = "error.form.reservation.repetition.interval.min")
    @Column(name = "repetition_interval")
    private Integer interval;
    @Future(message = "error.form.reservation.repetition.endDate.after")
    @Column(name = "repetition_end_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;
    
    @Transient
    private Collection<DayOfWeek> weekDays;
    

    public Repetition() {
    }

    public Repetition(RepetitionType type) {
        this.type = type;
        this.interval = 1;
        this.weekDays = null;
    }

//    public Repetition(RepetitionType type, Integer interval, Set<DayOfWeekWrapper> weekDays, Date endDate) {
//        this.type = type;
//        this.interval = interval;
//        this.weekDays = weekDays;
//        this.endDate = endDate;
//    }

    public Repetition(RepetitionType type, Integer interval, Set<DayOfWeek> weekDays, Date endDate) {
        this.type = type;
        this.interval = interval;
        this.setWeekDays(weekDays);
        this.endDate = endDate;
    }
    
    public RepetitionType getType() {
        return type;
    }

    public void setType(RepetitionType type) {
        this.type = type;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Date getEndDate() {
        return endDate;
    }

    @JsonIgnore
    public Calendar getEndDateAsCalendar() {
        if (this.endDate == null) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        return c;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

        public Collection<DayOfWeek> getWeekDays() {
            return weekDays;
        }
    
        public void setWeekDays(Collection<DayOfWeek> weekDays) {
            this.weekDays = weekDays;
        }
    
//    public Collection<DayOfWeek> getWeekDays() {
//        Collection<DayOfWeek> custom = new LinkedList<>();
//        for(DayOfWeekWrapper day: this.weekDays)
//            custom.add(day.getWeekDay());
//        
//        return custom;
//    }
//
//    public void setWeekDays(Collection<DayOfWeek> weekDays) {
//        this.weekDays = new LinkedList<>();
//        for(DayOfWeek day: weekDays)
//            this.weekDays.add(new DayOfWeekWrapper(day));
//    }
//    
//    public Collection<DayOfWeekWrapper> getWeekDaysAsWrapper() {
//        return weekDays;
//    }
//
//    public void setWeekDaysAsWrapper(Collection<DayOfWeekWrapper> weekDays) {
//        this.weekDays = weekDays;
//    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 29 * hash + Objects.hashCode(this.interval);
        hash = 29 * hash + Objects.hashCode(this.weekDays);
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
        final Repetition other = (Repetition) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.interval != other.interval) {
            return false;
        }
        if (!Objects.equals(this.weekDays, other.weekDays)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Repetition{" + "type=" + type + ", interval=" + interval + ", weekDays=" + weekDays + ", endDate=" + endDate + '}';
    }
}
