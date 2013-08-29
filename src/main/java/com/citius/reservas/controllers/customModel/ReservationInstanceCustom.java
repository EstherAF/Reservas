/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.customModel;

import com.citius.reservas.controllers.i18n.i18nManager;
import com.citius.reservas.models.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ReservationInstanceCustom extends ReservationInstance implements Serializable{
    private static final long serialVersionUID = 2L;
    private Locale locale;
    private i18nManager i18nController;

    public ReservationInstanceCustom() {
        super();
    }

    public ReservationInstanceCustom(Integer id, Reservation reservation, Date startTimeDate, Date endTimeDate, Locale locale, i18nManager i18nController) {
        super(reservation, startTimeDate, endTimeDate);
        this.locale = locale;
        this.i18nController = i18nController;
    }
    
    public ReservationInstanceCustom(ReservationInstance r, Locale locale, i18nManager i18nController) {
        this.setId(r.getId());
        this.setEndTimeDate(r.getEndTimeDate());
        this.setStartTimeDate(r.getStartTimeDate());
        this.setReservation(r.getReservation());
        this.locale = locale;
        this.i18nController = i18nController;
    }

    public String getStartTimeAsString() {
        return this.timeAsString(this.getStartTimeDate());
    }
    
    public String getEndTimeAsString() {
        return this.timeAsString(this.getEndTimeDate());
    }    
    
    private String timeAsString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String string = format.format(date);
        return string;
    }
    
    public String getStartDateAsString(){
        return this.dateAsString(this.getStartTimeDate());
    }
    
    public String getEndDateAsString(){
        return this.dateAsString(this.getEndTimeDate());
    }

    public String getStartDateAsStringShort(){
        return this.dateAsStringShort(this.getStartTimeDate());
    }
    
    public String getEndDateAsStringShort(){
        return this.dateAsStringShort(this.getEndTimeDate());
    }
    
    public String getStartWeeklyDay(){
        return this.weeklyDay(this.getStartTimeDate());
    }
    
    public String getEndWeeklyDay(){
        return this.weeklyDay(this.getEndTimeDate());
    }
    
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public i18nManager getI18nController() {
        return i18nController;
    }

    public void setI18nController(i18nManager i18nController) {
        this.i18nController = i18nController;
    }
    
    public String dateAsString(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Integer monthI = c.get(Calendar.MONTH) + 1;
        Integer monthlyDay = c.get(Calendar.DAY_OF_MONTH);
        Integer year = c.get(Calendar.YEAR);
        Integer weeklyDayI = c.get(Calendar.DAY_OF_WEEK);
        weeklyDayI = ((weeklyDayI + 5) % 7) + 1;

        String month = i18nController.getMessage(this.locale, null, "date.month." + monthI);
        String day = i18nController.getMessage(this.locale, null, "date.week.day." + weeklyDayI);

        String result = day + ", " + monthlyDay + " " + month + " " + year;

        return result;
    }
    
    public String weeklyDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Integer monthlyDay = c.get(Calendar.DAY_OF_MONTH);
        Integer weeklyDayI = c.get(Calendar.DAY_OF_WEEK);
        weeklyDayI = ((weeklyDayI + 5) % 7) + 1;

        String day = i18nController.getMessage(this.locale, null, "date.week.day." + weeklyDayI);

        String result = day + ", " + monthlyDay;

        return result;
    }
    
    public String dateAsStringShort(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Integer monthI = c.get(Calendar.MONTH) + 1;
        Integer monthlyDay = c.get(Calendar.DAY_OF_MONTH);
        
        String month = i18nController.getMessage(this.locale, null, "date.month." + monthI);

        String result = monthlyDay + " " + month;

        return result;
    }

    public Boolean isBetweenDates(Date startDate, Date endDate){
        
        Long start = startDate.getTime();
        Long end = endDate.getTime();
        Long startInstance = this.getStartTimeDate().getTime();
        Long endInstance = this.getEndTimeDate().getTime();
        
        return (
                (end > startInstance && startInstance > start) || 
                (end > endInstance && endInstance > start) ||
                (startInstance <= start && start < endInstance) ||
                (startInstance < end && end <= endInstance)
                );
        
        
    }
    
    
    @Override
    public Date getEndTimeDate(){
        Date date = new Date();
        date.setTime(super.getEndTimeDate().getTime());
        
        return date;
    }
    
}
