/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Embeddable
public class Repetition implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_type")
    private RepetitionType type;
    
    @Column(name = "repetition_interval")
    private int interval;
    
    
    @JoinTable( name = "weekDays_reservation",
        joinColumns = @JoinColumn(name = "reservation_id")
    ) 
    @Column(name = "week_day")
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(targetClass = DayOfWeek.class)
    private List<DayOfWeek> weekDays;

//    private int monthlyRelativeWeek;
    public Repetition() {
    }

    public Repetition(RepetitionType type) {
        this.type = type;
        this.interval = 0;
        this.weekDays = null;
    }

    public Repetition(RepetitionType type, int interval, List<DayOfWeek> weekDays) {
        this.type = type;
        this.interval = interval;
        this.weekDays = weekDays;
    }

    public RepetitionType getType() {
        return type;
    }

    public void setType(RepetitionType type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<DayOfWeek> getDays() {
        return weekDays;
    }

    public void setDays(List<DayOfWeek> weekDays) {
        this.weekDays = weekDays;
    }

//    public int getMonthlyRelativeWeek() {
//        return monthlyRelativeWeek;
//    }
//
//    public void setMonthlyRelativeWeek(int monthlyRelativeWeek) {
//        this.monthlyRelativeWeek = monthlyRelativeWeek;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        //queda por hacer!!
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass().equals(Repetition.class)) {
            Repetition r = (Repetition) object;

            return (weekDays == r.getDays()
                    && interval == r.getInterval()
                    && type == r.getType());
//                    &&
//               monthlyRelativeWeek == r.getMonthlyRelativeWeek());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        //queda por hacer
        return null;
    }
}
