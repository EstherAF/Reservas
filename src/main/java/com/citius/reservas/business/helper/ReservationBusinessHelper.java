/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.helper;

import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.DayOfWeek;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.models.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationBusinessHelper {
    public Repetition createWeeklyRepetition(int Interval, List<DayOfWeek> DaysOfWeek);
    
    public Date createDateTime(Calendar date, Calendar time);
    public Calendar createDateTimeAsCalendar(Calendar date, Calendar time);
    
    public List<ReservationInstance> generateInstances(Reservation r);
    
//    public List<ReservationInstance> dailyInstances(Reservation r);
//    
//    public List<ReservationInstance> weeklyInstances(Reservation r);
//    
//    public List<ReservationInstance> monthlyRelativeInstances(Reservation r);
//    
//    public List<ReservationInstance> monthlyInstances(Reservation r);
    
    public Date deleteTime(Date d);
    
    public void deleteTime(Calendar d);
    
    public void cleanTimeDate(Reservation r);
    
    public Repetition createRepetition(String repetitionType, Integer interval, List<Integer> daysOfWeek);
    
    public List<Resource> checkAvaliability(List<Integer> resourcesId, List<ReservationInstance> instances) throws NotAvaliableException;
    
    //public Integer compareResources(List<Resource> a, List<Resource> b);
    
    public Resource contains(List<Resource> listResources, Integer id);
    //public Resource getIfContains(List<Integer> listIds, Resource content);
}
