/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.helper.impl;

import com.citius.reservas.business.helper.ReservationBusinessHelper;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.DayOfWeek;
import com.citius.reservas.models.RepetitionType;
import static com.citius.reservas.models.RepetitionType.DAILY;
import static com.citius.reservas.models.RepetitionType.MONTHLY;
import static com.citius.reservas.models.RepetitionType.MONTHLY_RELATIVE;
import static com.citius.reservas.models.RepetitionType.ONCE;
import static com.citius.reservas.models.RepetitionType.WEEKLY;
import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import com.citius.reservas.repositories.ResourceRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class ReservationBusinessHelperImpl implements ReservationBusinessHelper {
    
    @Autowired
    private ResourceRepository resourceR;
    @Autowired
    private ReservationInstanceRepository instanceR;

    @Override
    public Repetition createWeeklyRepetition(int Interval, List<DayOfWeek> DaysOfWeek) {
        //Incompleto
        return new Repetition(WEEKLY, Interval, DaysOfWeek);
    }

  
    @Override
    public Calendar createDateTime(Calendar date, Calendar time) {
        Calendar c = new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH),
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, 0);

        return c;
    }
    
    @Override
    public List<ReservationInstance> dailyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        Integer interval = r.getRepetition().getInterval();

        Calendar iterator = (Calendar) r.getStartDate().clone();

        while (!iterator.after(r.getEndDate())) {
            Calendar start = createDateTime(iterator, r.getStartTime());
            Calendar end = createDateTime(iterator, r.getEndTime());

            l.add(new ReservationInstance(r, start, end));

            iterator.add(Calendar.DATE, interval);
        }

        return l;
    }

    @Override
    public List<ReservationInstance> weeklyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        int interval = r.getRepetition().getInterval();
        Calendar iterator = (Calendar) r.getStartDate().clone();

        // List of days of week
        List<Integer> days = new ArrayList<>();
        for (DayOfWeek d : r.getRepetition().getDays())
            days.add(d.getNumber());

        int initialDayOfWeek = DayOfWeek.fromCalendarDate(r.getStartDate()).getNumber();
        int advance = 7 - initialDayOfWeek;
        List<Integer> increasedDays = new ArrayList<>();

        Boolean lessWeek = false;

        for (Integer d : days)
            if (d < initialDayOfWeek) {
                increasedDays.add(d + advance);
                lessWeek = true;
            } else
                increasedDays.add(d);

        if (lessWeek)
            r.getEndDate().add(Calendar.DATE, -7);

        while (!iterator.after(r.getEndDate())) {
            for (Integer d : increasedDays) {
                iterator.add(Calendar.DATE, d);
                Calendar start = createDateTime(iterator, r.getStartTime());
                Calendar end = createDateTime(iterator, r.getEndTime());

                l.add(new ReservationInstance(r, start, end));
                iterator.add(Calendar.DATE, d * (-1));
            }
            iterator.add(Calendar.DATE, interval * 7);
        }
        
        r.getEndTime().add(Calendar.DATE, 7);

        return l;
    }

    @Override
    public List<ReservationInstance> monthlyRelativeInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        Integer interval = r.getRepetition().getInterval();

        int dayOfWeek = r.getStartDate().get(Calendar.DAY_OF_WEEK);
        int occurrence = this.getOccurrenceWeeklyDayOnMonth(r.getStartDate());
        int month = r.getStartDate().get(Calendar.MONTH);
        int year = r.getStartDate().get(Calendar.YEAR);
        
        
        Calendar start = r.getStartTime();
        start.set(r.getStartDate().get(Calendar.YEAR), 
                r.getStartDate().get(Calendar.MONTH),
                r.getStartDate().get(Calendar.DATE));
        Calendar end = r.getEndTime();
        end.set(r.getStartDate().get(Calendar.YEAR), 
                r.getStartDate().get(Calendar.MONTH),
                r.getStartDate().get(Calendar.DATE));
        
        while(! end.after(r.getEndDate())) {

            l.add(new ReservationInstance(r, start, end));

            month+=interval;
            
            if(month>11){
                month-=12;
                year++;
            }
            
            start = (Calendar) start.clone();
            end = (Calendar) end.clone();
            
            this.updateNextMonthlyRelativeDates(start, end, 
                    dayOfWeek, occurrence, month, year);
        }

        return l;
    }

    @Override
    public List<ReservationInstance> monthlyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        Integer interval = r.getRepetition().getInterval();

        Calendar iterator = (Calendar) r.getStartDate().clone();

        while (!iterator.after(r.getEndDate())) {
            
            l.add(new ReservationInstance(r, 
                    createDateTime(iterator, r.getStartTime()),
                    createDateTime(iterator, r.getEndTime())));

            iterator.add(Calendar.MONTH, interval);
        }

        return l;
    }

    @Override
    public List<ReservationInstance> generateInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();
        Calendar sTD, eTD;


        //Generación de las instancias de la reserva
        switch (r.getRepetition().getType()) {
            case ONCE:
                r.setEndDate(r.getStartDate());
                sTD = createDateTime(r.getStartDate(),r.getStartTime());
                eTD = createDateTime(r.getStartDate(),r.getEndTime());
                l.add(new ReservationInstance(r, sTD, eTD));
                break;
            case DAILY:
                l = dailyInstances(r);
                break;
            case WEEKLY:
                l = weeklyInstances(r);
                break;
            case MONTHLY:
                l = monthlyInstances(r);
                break;
            case MONTHLY_RELATIVE:
                l = monthlyRelativeInstances(r);
                break;
        }

        return l;
    }
    
    @Override
    public void deleteTime(Calendar c){
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
    }
    
    @Override
    public void cleanTimeDate(Reservation r){
        deleteTime(r.getStartDate());
        deleteTime(r.getEndDate());
        
        r.getStartTime().set(Calendar.SECOND, 0);
        r.getEndTime().set(Calendar.SECOND, 0);
    }
    
    private int getOccurrenceWeeklyDayOnMonth(Calendar c){
        
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int weeklyDay = c.get(Calendar.DAY_OF_WEEK);
        
        //Comienzo del mes
        c.set(Calendar.DATE,1);
        while(c.get(Calendar.DAY_OF_WEEK)!= weeklyDay){
            c.add(Calendar.DATE,1);    
        }
        
        int occurrence = 1;
        while(c.get(Calendar.DAY_OF_MONTH)!= dayOfMonth){
            //Siguiente semana
            occurrence++;
            c.add(Calendar.DATE,7);
        }
        
        return occurrence;
    }
    
    private void updateNextMonthlyRelativeDates(Calendar start, Calendar end, int dayOfWeek, int occurrence, int month, int year){
        
        //Al principio del mes
        start.set(year, month, 1);
        
        int incremento = dayOfWeek - start.get(Calendar.DAY_OF_WEEK);
        if(incremento<0)
            incremento+=7;
        
        //Primera ocurrencia de dayOfWeek
        start.add(Calendar.DATE, incremento);
        int tempOccurrence=1;
        
        int incrementoSemanas=occurrence-tempOccurrence;
        
        start.add(Calendar.DATE, 7*incrementoSemanas);
        
        while(start.get(Calendar.MONTH)!=month)
            start.add(Calendar.DATE, -1);

        end.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE));
    }

    @Override
    public Repetition createRepetition(String rType, Integer interval, List<Integer> days) {
         RepetitionType repetitionType = RepetitionType.valueOf(rType);

        List<DayOfWeek> daysOfWeek = null;
        if (repetitionType.equals(RepetitionType.WEEKLY)) 
            daysOfWeek = DayOfWeek.fromInteger(days);
        
        if(repetitionType.equals(RepetitionType.ONCE))
            interval=0;
        else if(interval == 0 && !repetitionType.equals(RepetitionType.ONCE))
            interval = 1;

        Repetition r = new Repetition(repetitionType, interval, daysOfWeek);
        
        return r;
    }

    @Override
    public List<Resource> checkAvaliability(List<Integer> resourcesId, List<ReservationInstance> instances) throws NotAvaliableException {
        List<Resource> resources = new ArrayList<>();
        for (Integer id : resourcesId) {
            Resource resource = resourceR.find(id);
            if(resource==null)
                throw new IllegalArgumentException("Resource " + id + " can't be found");
            else{
                //Check if resource is avaliable
                for(ReservationInstance instance:instances){
                    if(!instanceR.isAvaliable(resource, instance.getStartTimeDate(), instance.getEndTimeDate()))
                        throw new NotAvaliableException("Resource "+resource.getName()+" is already reserved");
                }
                resources.add(resource);
            }
        }
        return resources;
    }

    @Override
    public Resource contains(List<Resource> resources, Integer id) {
        for(Resource r: resources){
            if(r.getId().equals(id))
                return r;
        }
        return null;
    }
}

