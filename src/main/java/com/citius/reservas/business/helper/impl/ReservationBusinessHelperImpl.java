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
import static com.citius.reservas.models.RepetitionType.*;
import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.ReservationInstanceRepository;
import com.citius.reservas.repositories.ResourceRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Ã?lvarez Feijoo
 */
@Component
public class ReservationBusinessHelperImpl implements ReservationBusinessHelper {

    private Calendar iterator;
    private long duration;
    private Calendar endRepetitionDate;
    private int interval;

    

//    @Override
//    public Set<Resource> checkAvaliability(List<Integer> resourcesId,
//            Set<ReservationInstance> instances)
//            throws NotAvaliableException {
//        Set<Resource> resources = new LinkedHashSet<>();
//
//        for (Integer id : resourcesId) {
//
//            Resource resource = resourceR.find(id);
//            if (resource == null) {
//                throw new IllegalArgumentException("Resource " + id + " can't be found");
//            } else {
//                resources.add(resource);
//            }
//        }
//        
//        resources = this.checkAvaliability(resources, instances);
//        
//        return resources;
//    }
//
//    @Override
//    public Set<Resource> checkAvaliability(Set<Resource> resources,
//            Set<ReservationInstance> instances)
//            throws NotAvaliableException {
//
//        for (Resource resource : resources) {
//
//            //Check if resource is avaliable
//            for (ReservationInstance instance : instances) {
//                if (!instanceR.isAvaliable(resource, instance.getStartTimeDate(), instance.getEndTimeDate())) {
//                    throw new NotAvaliableException("Resource " + resource.getName() + " is already reserved");
//                }
//            }
//        }
//        return resources;
//    }

//    @Override
//    public Resource contains(Set<Resource> resources, Integer id) {
//        for (Resource r : resources) {
//            if (r.getId().equals(id)) {
//                return r;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Date deleteTime(Date d) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(d);
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 0);
//        return c.getTime();
//    }

    /**
     * ************************************************
     * PRIVATES ************************************************
     */
    
}
