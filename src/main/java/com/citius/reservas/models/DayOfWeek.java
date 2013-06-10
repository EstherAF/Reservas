/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.models;

import java.util.Calendar;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public enum DayOfWeek {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);
    private int number;

    private DayOfWeek(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static DayOfWeek fromInteger(Integer x) {
        switch (x) {
            case 1:
                return MONDAY;
            case 2:
                return TUESDAY;
            case 3:
                return WEDNESDAY;
            case 4:
                return THURSDAY;
            case 5:
                return FRIDAY;
            case 6:
                return SATURDAY;
            case 7:
                return SUNDAY;
            default:
                return null;
        }
        
    }
    
    public static DayOfWeek fromCalendarDate(Calendar day) {
        Integer calendarDay = day.get(Calendar.DAY_OF_WEEK);
        
        switch (calendarDay) {
            case Calendar.MONDAY:
                return MONDAY;
            case Calendar.TUESDAY:
                return TUESDAY;
            case Calendar.WEDNESDAY:
                return WEDNESDAY;
            case Calendar.THURSDAY:
                return THURSDAY;
            case Calendar.FRIDAY:
                return FRIDAY;
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.SUNDAY:
                return SUNDAY;
            default: 
                return null;
        }
    }
    
    public static int toCalendar(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return  Calendar.MONDAY;
            case TUESDAY:
                return  Calendar.TUESDAY;
            case WEDNESDAY:
                return  Calendar.WEDNESDAY;
            case THURSDAY:
                return  Calendar.THURSDAY;
            case FRIDAY:
                return Calendar.FRIDAY;
            case SATURDAY:
                return  Calendar.SATURDAY;
            case SUNDAY:
                return  Calendar.SUNDAY;
            default:
                return 0;
        }

    }
}
