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
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.EventTimeDate;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.User;
import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public Reservation createReservation(Reservation r) throws NotAvaliableException{
        r.setId(null);
        
        User owner = ur.findByUniqueName(r.getOwner().getUniqueName());
        r.setOwner(owner);
        
        r.setInstances(rbh.generateInstances(r));     
        
        List<Integer> resourcesId = new ArrayList<>();
        for(Resource res:r.getResources())
            resourcesId.add(res.getId());
        
        r.setResources(rbh.checkAvaliability(resourcesId, r.getInstances()));
        
        rbh.cleanTimeDate(r);
        r = rr.create(r);
        return r;
    }
    
    @Override
    @Transactional
    public Reservation createReservation(
            String name, 
            String description, 
            String ownerUniqueName,
            Date startDate, Date endDate, Date startTime, Date endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException{
        
        if(resourcesId==null || resourcesId.isEmpty())
            throw new IllegalArgumentException("Reservation without resources can't be created");
        
        User owner = ur.findByUniqueName(ownerUniqueName);
        
        EventTimeDate timeDate= new EventTimeDate(startDate, endDate, startTime, endTime);
        Reservation r = new Reservation(name, description, owner, timeDate);
        
        r.setRepetition(rbh.createRepetition(rType, interval, days));
        r.setInstances(rbh.generateInstances(r));     
        r.setResources(rbh.checkAvaliability(resourcesId, r.getInstances()));
        
        rbh.cleanTimeDate(r);
        
        r = rr.create(r);

        return r;
    }

    @Override
    @Transactional
    public Reservation saveReservation(Integer id, String name, String description,
            Date startDate, Date endDate, Date startTime, Date endTime,
            String rType, int interval, List<Integer> days,
            List<Integer> resourcesId) throws NotAvaliableException{

        Reservation found = rr.find(id);
        found.setName(name);
        found.setName(description);

        Repetition repetition = rbh.createRepetition(rType, interval, days);

        //Have to update the instances
        if (!found.getEventTimeDate().equals(found.getEventTimeDate())
                || !found.getRepetition().equals(repetition)) {

            found.setEventTimeDate(new EventTimeDate(startDate, endDate, startTime, endTime));
            rbh.cleanTimeDate(found);
            
            found.setRepetition(repetition);

            found.setInstances(rbh.generateInstances(found));

        }
        
        //Resources have changed?
        List<Integer> haveToCheck = new ArrayList<>();
        List<Resource> newResourceList=new ArrayList<>();
        if(resourcesId.size()>found.getResources().size()){
            //There are new resources
            for(Integer idResource: resourcesId){
                Resource alreadyReserved = rbh.contains(found.getResources(), idResource);
                if(alreadyReserved==null){
                    haveToCheck.add(idResource);
                }else
                    newResourceList.add(alreadyReserved);
            }
        }
        
        if(!haveToCheck.isEmpty()){
            newResourceList.addAll(
                        rbh.checkAvaliability(haveToCheck, found.getInstances()));
            found.setResources(newResourceList);
        }
        else if(resourcesId.size() != found.getResources().size()){
            found.setResources(newResourceList);
        }
        
        //If haveToCheck.isEmpty and sizes are equals, resources don't change
        return rr.save(found);

    }

    @Override
    @Transactional
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
    public List<ReservationInstance> readByWeek(String ownerUniqueName, Calendar week) {
        
        week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        rbh.deleteTime(week);
        
        Date init = week.getTime();

        week.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        week.add(Calendar.DATE, 1);
        Date end = week.getTime();



        List<ReservationInstance> l = rir.findBetweenDatesWithInvitations(ownerUniqueName, init, end);
        week.add(Calendar.DATE, -1);
        return l;
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

        return rir.findBetweenDatesWithInvitations(ownerUniqueName, first.getTime(), last.getTime());
    }

    @Override
    @Transactional
    public Boolean isOwner(Integer reservationId, String ownerUniqueName) {
        Reservation r = rr.find(reservationId);
        return r.getOwner().getUniqueName().equals(ownerUniqueName);
    }

    @Override
    public List<ReservationInstance> readAll() {
        return rir.findAll();
    }

    
}
