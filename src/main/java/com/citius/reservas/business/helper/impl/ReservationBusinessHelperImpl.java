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
import java.util.Date;
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
    private Calendar iterator;
    private Calendar endDate;
    private Calendar startTime;
    private Calendar endTime;
    int interval;

    @Override
    public Repetition createWeeklyRepetition(int Interval, List<DayOfWeek> DaysOfWeek) {
        //Incompleto
        return new Repetition(WEEKLY, Interval, DaysOfWeek);
    }

    @Override
    public Calendar createDateTimeAsCalendar(Calendar date, Calendar time) {
        Calendar c = new GregorianCalendar(date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH),
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, 0);

        return c;
    }

    @Override
    public Date createDateTime(Calendar date, Calendar time) {
        Calendar c = createDateTimeAsCalendar(date, time);
        return c.getTime();
    }

    @Override
    public List<ReservationInstance> generateInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();
        Calendar sTD, eTD;


        iterator = r.getEventTimeDate().getStartDateAsCalendar();
        endDate = r.getEventTimeDate().getEndDateAsCalendar();
        startTime = r.getEventTimeDate().getStartTimeAsCalendar();
        endTime = r.getEventTimeDate().getEndTimeAsCalendar();
        interval = r.getRepetition().getInterval();


        //Generación de las instancias de la reserva
        switch (r.getRepetition().getType()) {
            case ONCE:
                r.getEventTimeDate().setEndDate(iterator.getTime());
                l.add(newReservationInstance(r, iterator, startTime, endTime));
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
    public Repetition createRepetition(String rType, Integer interval, List<Integer> days) {
        RepetitionType repetitionType = RepetitionType.valueOf(rType);

        List<DayOfWeek> daysOfWeek = null;
        if (repetitionType.equals(RepetitionType.WEEKLY)) {
            daysOfWeek = DayOfWeek.fromInteger(days);
        }

        if (repetitionType.equals(RepetitionType.ONCE)) {
            interval = 0;
        } else if (interval == 0 && !repetitionType.equals(RepetitionType.ONCE)) {
            interval = 1;
        }

        Repetition r = new Repetition(repetitionType, interval, daysOfWeek);

        return r;
    }

    @Override
    public List<Resource> checkAvaliability(List<Integer> resourcesId, List<ReservationInstance> instances) throws NotAvaliableException {
        List<Resource> resources = new ArrayList<>();
        for (Integer id : resourcesId) {
            Resource resource = resourceR.find(id);
            if (resource == null) {
                throw new IllegalArgumentException("Resource " + id + " can't be found");
            } else {
                //Check if resource is avaliable
                for (ReservationInstance instance : instances) {
                    if (!instanceR.isAvaliable(resource, instance.getStartTimeDate(), instance.getEndTimeDate())) {
                        throw new NotAvaliableException("Resource " + resource.getName() + " is already reserved");
                    }
                }
                resources.add(resource);
            }
        }
        return resources;
    }

    @Override
    public Resource contains(List<Resource> resources, Integer id) {
        for (Resource r : resources) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void cleanTimeDate(Reservation r) {
        Date startDateD = deleteTime(r.getEventTimeDate().getStartDate());
        Date endDateD = deleteTime(r.getEventTimeDate().getEndDate());
        
        Date startTimeD = cleanSeconds(r.getEventTimeDate().getStartTime());
        Date endTimeD = cleanSeconds(r.getEventTimeDate().getEndTime());
        
        r.getEventTimeDate().setStartDate(startDateD);
        r.getEventTimeDate().setEndDate(endDateD);
        r.getEventTimeDate().setStartTime(startTimeD);
        r.getEventTimeDate().setEndTime(endTimeD);
    }
    
    @Override
    public Date deleteTime(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        deleteTime(c);
        return c.getTime();
    }
    
    @Override
    public void deleteTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
    }
    

    /**
     * ************************************************
     * PRIVATES
     *************************************************
     */
    private List<ReservationInstance> dailyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        while (!iterator.after(endDate)) {
            l.add(newReservationInstance(r, iterator, startTime, endTime));
            iterator.add(Calendar.DATE, interval);
        }

        return l;
    }

    private List<ReservationInstance> weeklyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        // List of days of week
        List<Integer> days = new ArrayList<>();
        for (DayOfWeek d : r.getRepetition().getDays()) {
            days.add(d.getNumber());
        }

        int initialDayOfWeek = DayOfWeek.fromCalendarDate(iterator).getNumber();
        int advance = 7 - initialDayOfWeek;
        List<Integer> increasedDays = new ArrayList<>();


        //Normalize days of week depends on the day of week of the startDay
        Boolean lessWeek = false;
        for (Integer d : days) {
            if (d < initialDayOfWeek) {
                increasedDays.add(d + advance);
                lessWeek = true;
            } else {
                increasedDays.add(d);
            }
        }
        if (lessWeek) {
            endDate.add(Calendar.DATE, -7);
        }

        while (!iterator.after(endDate)) {
            for (Integer d : increasedDays) {
                iterator.add(Calendar.DATE, d);
                l.add(newReservationInstance(r, iterator, startTime, endTime));
                iterator.add(Calendar.DATE, d * (-1));
            }
            iterator.add(Calendar.DATE, interval * 7);
        }

        return l;
    }

    private List<ReservationInstance> monthlyRelativeInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();

        int dayOfWeek = iterator.get(Calendar.DAY_OF_WEEK);
        int occurrence = this.getOccurrenceWeeklyDayOnMonth(iterator);
        int month = iterator.get(Calendar.MONTH);
        int year = iterator.get(Calendar.YEAR);


        Calendar start = startTime;
        start.set(iterator.get(Calendar.YEAR),
                iterator.get(Calendar.MONTH),
                iterator.get(Calendar.DATE));
        Calendar end = endTime;
        end.set(iterator.get(Calendar.YEAR),
                iterator.get(Calendar.MONTH),
                iterator.get(Calendar.DATE));

        while (!end.after(endDate)) {

            l.add(new ReservationInstance(r, start.getTime(), end.getTime()));

            //Next month
            month += interval;
            if (month > 11) {
                month -= 12;
                year++;
            }

            start = (Calendar) start.clone();
            end = (Calendar) end.clone();

            this.updateNextMonthlyRelativeDates(start, end,
                    dayOfWeek, occurrence, month, year);
        }

        return l;
    }

    private List<ReservationInstance> monthlyInstances(Reservation r) {
        List<ReservationInstance> l = new ArrayList<>();


        while (!iterator.after(endDate)) {
            l.add(newReservationInstance(r, iterator, startTime, endTime));
            iterator.add(Calendar.MONTH, interval);
        }

        return l;
    }

    private int getOccurrenceWeeklyDayOnMonth(Calendar c) {

        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int weeklyDay = c.get(Calendar.DAY_OF_WEEK);

        //Comienzo del mes
        c.set(Calendar.DATE, 1);
        while (c.get(Calendar.DAY_OF_WEEK) != weeklyDay) {
            c.add(Calendar.DATE, 1);
        }

        int occurrence = 1;
        while (c.get(Calendar.DAY_OF_MONTH) != dayOfMonth) {
            //Siguiente semana
            occurrence++;
            c.add(Calendar.DATE, 7);
        }

        return occurrence;
    }

    private void updateNextMonthlyRelativeDates(Calendar start, Calendar end, int dayOfWeek, int occurrence, int month, int year) {

        //Al principio del mes
        start.set(year, month, 1);

        int incremento = dayOfWeek - start.get(Calendar.DAY_OF_WEEK);
        if (incremento < 0) {
            incremento += 7;
        }

        //Primera ocurrencia de dayOfWeek
        start.add(Calendar.DATE, incremento);
        int tempOccurrence = 1;

        int incrementoSemanas = occurrence - tempOccurrence;

        start.add(Calendar.DATE, 7 * incrementoSemanas);

        while (start.get(Calendar.MONTH) != month) {
            start.add(Calendar.DATE, -1);
        }

        end.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE));
    }

    private ReservationInstance newReservationInstance(Reservation reservation,
            Calendar date, Calendar startTime, Calendar endTime) {

        return new ReservationInstance(reservation,
                createDateTime(date, startTime),
                createDateTime(date, endTime));
    }
    
    private Date cleanSeconds(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
}
