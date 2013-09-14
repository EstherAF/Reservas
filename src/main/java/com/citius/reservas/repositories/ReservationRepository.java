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
    /**
     *
     * @param name
     * @return
     */
    public List<Reservation> findByName(String name);
    /**
     *
     * @param unique_name
     * @return
     */
    public List<Reservation> findByOwner(String unique_name);
    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Reservation> findBetweenDates(Calendar startDate, Calendar endDate);
    /**
     *
     * @param resource
     * @return
     */
    public List<Reservation> findByResource(Resource resource);
    /**
     *
     * @param group
     * @return
     */
    public List<Reservation> findByResourceGroup(ResourceGroup group);
}
