/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationRepository extends GenericRepository<Reservation>{
    public List<Reservation> findByName(String name);
    public List<Reservation> findByOwner(String unique_name);
    public List<Reservation> findBetweenDates(Calendar startDate, Calendar endDate);
    public List<Reservation> findByResource(Resource resource);
    public List<Reservation> findByResourceGroup(ResourceGroup group);
}
