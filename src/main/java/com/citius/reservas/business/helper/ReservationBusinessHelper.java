/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.helper;

import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.types.DayOfWeek;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationBusinessHelper {
    public Repetition createWeeklyRepetition(int Interval, List<DayOfWeek> DaysOfWeek);
    
    public Calendar createDateTime(Calendar date, Calendar time);
    
    public List<ReservationInstance> generateInstances(Reservation r);
    
    public  List<ReservationInstance> dailyInstances(Reservation r);
    
    public List<ReservationInstance> weeklyInstances(Reservation r);
    
    public List<ReservationInstance> monthlyRelativeInstances(Reservation r);
    
    public List<ReservationInstance> monthlyInstances(Reservation r);
    
    public void deleteTime(Calendar c);
    
    public void cleanTimeDate(Reservation r);
}
