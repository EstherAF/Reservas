/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import com.citius.reservas.repositories.ReservationRepository;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.business.helper.ReservationBusinessHelper;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.User;
import com.citius.reservas.models.DayOfWeek;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Service
public class ReservationBusinessImpl implements ReservationBusiness {

    @Autowired
    private ReservationRepository rr;
    @Autowired
    private ReservationInstanceRepository rir;
    @Autowired
    private ReservationBusinessHelper rbh;
    @Autowired
    private UserRepository ur;

    @Override
    @Transactional
    public Reservation createReservation(String name, String description, String ownerUniqueName,
            Calendar startDate, Calendar endDate, Calendar startTime, Calendar endTime,
            String rType, int interval, List<Integer> days) {

        RepetitionType repetitionType = RepetitionType.valueOf(rType);
        
        //Create ReservationInstances
        User owner = ur.findByUniqueName(ownerUniqueName);

        //Cast List<Integer> daysOfWeek to List<DayOfWeek>
        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        if (repetitionType.equals(RepetitionType.WEEKLY))
            for (Integer d : days)
                daysOfWeek.add(DayOfWeek.fromInteger(d));

        if (interval == 0 && !repetitionType.equals(RepetitionType.ONCE))
            interval = 1;
        

        Repetition rep = new Repetition(repetitionType, interval, daysOfWeek);

        Reservation r = new Reservation(name, description, owner,
                startDate, endDate, startTime, endTime, rep);
        
        rbh.cleanTimeDate(r);

        //Reservation's instances
        r.setInstances(rbh.generateInstances(r));
        r = rr.create(r);

        return r;
    }

    @Override
    @Transactional
    public Reservation saveReservation(Integer id, String name, String description,
            Calendar startDate, Calendar endDate, Calendar startTime, Calendar endTime,
            String rType, int interval, List<Integer> days) {

        Reservation found = rr.find(id);
        found.setName(name);
        found.setName(description);

        RepetitionType repetitionType = RepetitionType.valueOf(rType);

        List<DayOfWeek> daysOfWeek = new ArrayList<>();

        if (repetitionType.equals(RepetitionType.WEEKLY)) {
            for (Integer d : days) {
                daysOfWeek.add(DayOfWeek.fromInteger(d));
            }
        }
        
        if (interval == 0 && !repetitionType.equals(RepetitionType.ONCE)) {
            interval = 1;
        }

        Repetition r = new Repetition(repetitionType, interval, daysOfWeek);

        if (!found.getEndDate().equals(endDate)
                || !found.getStartDate().equals(startDate)
                || !found.getEndTime().equals(endTime)
                || !found.getStartTime().equals(startTime)
                || !found.getRepetition().equals(r)) {

            found.setDateTime(startDate, endDate, startTime, endTime);
            rbh.cleanTimeDate(found);
            
            found.setRepetition(r);

            found.setInstances(rbh.generateInstances(found));

        }

        return rr.save(found);

    }

    @Override
    public void deleteReservation(Integer id) {
        rr.delete(id);
    }

    @Override
    @Transactional
    public Reservation read(Integer reservation_id) {
        return rr.find(reservation_id);
    }

    @Override
    @Transactional
    public List<ReservationInstance> readByWeek(String ownerUniqueName, Integer week, Integer year) {
        Calendar monday = Calendar.getInstance();
        monday.set(Calendar.YEAR, year);
        monday.set(Calendar.WEEK_OF_YEAR, week);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        rbh.deleteTime(monday);

        Calendar nextMonday = (Calendar) monday.clone();
        nextMonday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        nextMonday.add(Calendar.DATE, 1);

        return rir.findBetweenDates(ownerUniqueName, monday, nextMonday);
    }

    @Override
    @Transactional
    public List<ReservationInstance> readByMonth(String ownerUniqueName, Integer month, Integer year) {
        Calendar first = Calendar.getInstance();
        first.set(Calendar.YEAR, year);
        first.set(Calendar.MONTH, month);
        first.set(Calendar.DAY_OF_MONTH, 1);
        first.set(Calendar.HOUR_OF_DAY, 0);
        first.set(Calendar.MINUTE, 0);
        first.set(Calendar.SECOND, 0);

        Calendar last = (Calendar) first.clone();
        last.add(Calendar.MONTH, 1);

        return rir.findBetweenDates(ownerUniqueName, first, last);
    }

    @Override
    @Transactional
    public List<ReservationInstance> readByOwnerAfterDate(String ownerUniqueName, Calendar startDate) {
        return rir.findAfterDate(ownerUniqueName, startDate);
    }

    @Override
    @Transactional
    public Boolean isOwner(Integer reservationId, String ownerUniqueName) {
        Reservation r = rr.find(reservationId);
        return r.getOwner().getUniqueName().equals(ownerUniqueName);
    }

    
}
