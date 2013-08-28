/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.exceptions.NotAvaliable;
import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import com.citius.reservas.repositories.ReservationRepository;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.controllers.customModel.ResourceGroupCustom;
import com.citius.reservas.exceptions.*;
import com.citius.reservas.models.DayOfWeek;
import static com.citius.reservas.models.RepetitionType.*;
import com.citius.reservas.models.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther √?lvarez Feijoo
 */
@Service
public class ReservationBusinessImpl implements ReservationBusiness {

    @Autowired
    private ReservationRepository rr;
    @Autowired
    private ReservationInstanceRepository rir;
    @Autowired
    private ResourceBusiness resourceBusiness;
    @Autowired
    private AccessBusiness accessBusiness;

    @Override
    public void deleteReservation(Integer id) {
        rr.delete(id);
    }

    @Override
    public Reservation read(Integer reservation_id) {
        return rr.find(reservation_id);
    }

    private Date[] prepareWeek(Calendar week) {
        Date[] dates = new Date[2];

        week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        dates[0] = week.getTime();
        week.add(Calendar.DATE, 7);
        dates[1] = week.getTime();

        week.add(Calendar.DATE, -7);

        return dates;
    }

    @Override
    public List<ReservationInstance> readByWeek(Calendar week) {
        Date[] dates = this.prepareWeek(week);
        return rir.findAllBetweenDates(dates[0], dates[1]);
    }

    @Override
    public List<ReservationInstance> readByWeekByUser(String ownerUniqueName, Calendar week) {
        Date[] dates = this.prepareWeek(week);
        return rir.findBetweenDatesByUser(ownerUniqueName, dates[0], dates[1]);
    }

    @Override
    public List<ReservationInstance> readByWeekByResource(Integer resourceId, Calendar week) {
        Date[] dates = this.prepareWeek(week);
        return rir.findAllBetweenDatesByResource(dates[0], dates[1], resourceId);
    }

    @Override
    public List<ReservationInstance> readByWeekByResourceGroup(Integer resourceGroupId, Calendar week) {
        Date[] dates = this.prepareWeek(week);
        return rir.findAllBetweenDatesByResourceGroup(dates[0], dates[1], resourceGroupId);
    }

    private Date[] prepareMonth(Integer month, Integer year) {
        Date[] dates = new Date[2];

        Calendar date = Calendar.getInstance();
        date.set(year, month, 1, 0, 0, 0);

        dates[0] = date.getTime();

        date.add(Calendar.MONTH, 1);
        dates[1] = date.getTime();

        return dates;
    }

    @Override
    public List<ReservationInstance> readByMonth(Integer month, Integer year) {
        Date[] dates = prepareMonth(month, year);
        return rir.findAllBetweenDates(dates[0], dates[1]);
    }

    @Override
    public List<ReservationInstance> readByMonthByUser(String userUniqueName, Integer month, Integer year) {
        Date[] dates = prepareMonth(month, year);
        return rir.findBetweenDatesByUser(userUniqueName, dates[0], dates[1]);
    }

    @Override
    public List<ReservationInstance> readByMonthByResource(Integer resourceId, Integer month, Integer year) {
        Date[] dates = prepareMonth(month, year);
        return rir.findAllBetweenDatesByResource(dates[0], dates[1], resourceId);
    }

    @Override
    public List<ReservationInstance> readByMonthByResourceGroup(Integer resourceGroupId, Integer month, Integer year) {
        Date[] dates = prepareMonth(month, year);
        return rir.findAllBetweenDatesByResourceGroup(dates[0], dates[1], resourceGroupId);
    }

    @Override
    public Boolean isOwner(Integer reservationId, String ownerUniqueName) {
        Reservation r = rr.find(reservationId);
        if (r == null) {
            return null;
        }

        return r.getOwner().getUniqueName().equals(ownerUniqueName);
    }

    @Override
    public List<ReservationInstance> readAll() {
        return rir.findAll();
    }

    @Override
    public Reservation saveReservation(Reservation reservation, List<ResourceGroupCustom> groups) throws NotAvaliableException, NotPossibleInstancesException {

        Set<ReservationInstance> instances = this.generateInstances(reservation);

        if (instances.isEmpty()) {
            throw new NotPossibleInstancesException("Not posible instances");
        }


        List<NotAvaliable> notAvaliableResources = new ArrayList<>();
        reservation.setInstances(instances);

        //Check avaliability of child resources
        reservation.setResources(this.getAvaliabiliableResources(
                reservation.getResources(), reservation.getInstances()));

        if (groups == null) {
            return rr.save((Reservation) reservation);
        }

        for (ResourceGroupCustom resourceToReserve : groups) {

            Set<Resource> avaliableResources = new HashSet<>();

            Boolean firstResultSearch = true;
            for (ReservationInstance instance : instances) {

                Date start = instance.getStartTimeDate();
                Date end = instance.getEndTimeDate();

                List<Resource> avaliablePerInstance = resourceBusiness.readAvaliableByGroupBetweenDates(resourceToReserve.getId(), start, end);

                //Intersection of list of posible resources, and avaliable resources in this instance
                if (firstResultSearch) {
                    firstResultSearch = false;
                    avaliableResources.addAll(avaliablePerInstance);
                } else {
                    avaliableResources.retainAll(avaliablePerInstance);
                }

                //Build error if ther aren't enough avaliable resources
                if (avaliablePerInstance.size() < resourceToReserve.getQuantity()) {
                    notAvaliableResources.add(new NotAvaliable(resourceToReserve.getName(), instance, avaliablePerInstance.size()));
                    break;
                }
            }

            /*End of search in this group*/

            //If there are more than neccesary, choose from the start
            if (avaliableResources.size() > resourceToReserve.getQuantity()) {
                List<Resource> avaliableResourcesList = new ArrayList<>(avaliableResources);
                for (int i = 0; i < resourceToReserve.getQuantity(); i++) {
                    reservation.addResource(avaliableResourcesList.get(i));
                }
            } else {
                reservation.addResources(avaliableResources);
            }
        }

        if (!notAvaliableResources.isEmpty()) {
            throw new NotAvaliableException(notAvaliableResources);
        }

        return rr.save((Reservation) reservation);

    }

    @Override
    public Reservation updateReservation(Reservation reservation, List<ResourceGroupCustom> groups) throws NotAvaliableException, NotPossibleInstancesException, UnknownResourceException {

        if(this.rr.find(reservation.getId())==null)
            throw new UnknownResourceException();
            
        return this.saveReservation(reservation, groups);
        
    }

    private Set<Resource> getAvaliabiliableResources(Set<Resource> resources,
            Set<ReservationInstance> instances)
            throws NotAvaliableException {

        List<NotAvaliable> notAvaliableResources = new ArrayList<>();
        for (Resource resource : resources) {

            //Check if resource is avaliable
            for (ReservationInstance instance : instances) {
                if (!rir.isAvaliable(resource, instance.getStartTimeDate(), instance.getEndTimeDate())) {
                    notAvaliableResources.add(new NotAvaliable(resource.getName(), instance, 0));
                    break;
                }
            }
        }

        //If there are no errors
        if (notAvaliableResources.isEmpty()) {
            return resources;
        }

        throw new NotAvaliableException(notAvaliableResources);

    }

    private Set<ReservationInstance> generateInstances(Reservation r) {
        Set<ReservationInstance> l = new LinkedHashSet<>();

        Calendar iterator = r.startAsACalendar();
        long duration = r.getDuration();
        Calendar endRepetitionDate = r.getRepetition().getEndDateAsCalendar();
        int interval = r.getRepetition().getInterval();


        //Generaci√≥n de las instancias de la reserva
        switch (r.getRepetition().getType()) {
            case ONCE:
                l.add(new ReservationInstance(r, iterator.getTime(), duration));
                break;
            case DAILY:
                l = dailyInstances(r, iterator, duration, interval, endRepetitionDate);
                break;
            case WEEKLY:
                l = weeklyInstances(r, iterator, duration, interval, endRepetitionDate);
                break;
            case MONTHLY:
                l = monthlyInstances(r, iterator, duration, interval, endRepetitionDate);
                break;
            case MONTHLY_RELATIVE:
                l = monthlyRelativeInstances(r, iterator, duration, interval, endRepetitionDate);
                break;
        }

        return l;
    }

    private Set<ReservationInstance> dailyInstances(Reservation r,
            Calendar iterator, Long duration, Integer interval, Calendar endRepetitionDate) {
        Set<ReservationInstance> l = new LinkedHashSet<>();

        while (!iterator.after(endRepetitionDate)) {

            l.add(new ReservationInstance(r, iterator.getTime(), duration));

            iterator.add(Calendar.DATE, interval);
        }

        return l;
    }

    private Set<ReservationInstance> weeklyInstances(Reservation r,
            Calendar iterator, Long duration, Integer interval, Calendar endRepetitionDate) {
        Set<ReservationInstance> l = new LinkedHashSet<>();

        // List of days of week
        List<Integer> days = new ArrayList<>();
        for (DayOfWeek d : r.getRepetition().getWeekDays()) {
            days.add(d.getNumber());
        }

        int initialDayOfWeek = DayOfWeek.fromCalendarDate(iterator).getNumber();
        int advance = 7 - initialDayOfWeek;
        List<Integer> normalizedDays = new ArrayList<>();


        //Normalize days of week depends on the day of week of the startDay
        for (Integer d : days) {
            if (d < initialDayOfWeek) {
                normalizedDays.add(d + advance);
            } else {
                normalizedDays.add(d - initialDayOfWeek);
            }
        }


        while (!iterator.after(endRepetitionDate)) {
            for (Integer d : normalizedDays) {
                iterator.add(Calendar.DATE, d);
                if (!iterator.after(endRepetitionDate)) {
                    l.add(new ReservationInstance(r, iterator.getTime(), duration));
                } else {
                    break;
                }
                iterator.add(Calendar.DATE, d * (-1));
            }

            iterator.add(Calendar.DATE, interval * 7);
        }

        return l;
    }

    private Set<ReservationInstance> monthlyRelativeInstances(Reservation r,
            Calendar iterator, Long duration, Integer interval, Calendar endRepetitionDate) {
        Set<ReservationInstance> l = new LinkedHashSet<>();

        int dayOfWeek = iterator.get(Calendar.DAY_OF_WEEK);
        int occurrence = this.getOccurrenceWeeklyDayOnMonth(iterator);
        int month = iterator.get(Calendar.MONTH);
        int year = iterator.get(Calendar.YEAR);


        while (!iterator.after(endRepetitionDate)) {

            l.add(new ReservationInstance(r, iterator.getTime(), duration));

            //Next month
            month += interval;
            if (month > 11) {
                month -= 12;
                year++;
            }

            this.updateNextMonthlyRelativeDates(iterator,
                    dayOfWeek, occurrence, month, year);
        }

        return l;
    }

    private Set<ReservationInstance> monthlyInstances(Reservation r,
            Calendar iterator, Long duration, Integer interval, Calendar endRepetitionDate) {
        Set<ReservationInstance> l = new LinkedHashSet<>();


        while (!iterator.after(endRepetitionDate)) {
            l.add(new ReservationInstance(r, iterator.getTime(), duration));
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

    private void updateNextMonthlyRelativeDates(Calendar start, int dayOfWeek, int occurrence, int month, int year) {

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
    }

    @Override
    public Boolean canEdit(Integer reservationId, String uniqueName) {
        return (this.isOwner(reservationId, uniqueName) || accessBusiness.isAdmin(uniqueName));
    }
}
