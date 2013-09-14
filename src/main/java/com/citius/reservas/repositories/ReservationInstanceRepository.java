/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public interface ReservationInstanceRepository extends GenericRepository<ReservationInstance> {
    
    /**
     *
     * @param startTimeDate
     * @param endTimeDate
     * @return
     */
    public List<ReservationInstance> findAllBetweenDates(Date startTimeDate, Date endTimeDate);
    /**
     *
     * @param startTimeDate
     * @param endTimeDate
     * @param resourceId
     * @return
     */
    public List<ReservationInstance> findAllBetweenDatesByResource(Date startTimeDate, Date endTimeDate, Integer resourceId);
    /**
     *
     * @param startTimeDate
     * @param endTimeDate
     * @param resourceGroupId
     * @return
     */
    public List<ReservationInstance> findAllBetweenDatesByResourceGroup(Date startTimeDate, Date endTimeDate, Integer resourceGroupId);
    
    /**
     *
     * @param ownerUniqueName
     * @param day
     * @return
     */
    public List<ReservationInstance> findByDayByUser(String ownerUniqueName, Date day);
    /**
     *
     * @param userUniqueName
     * @param startTimeDate
     * @param endTimeDate
     * @return
     */
    public List<ReservationInstance> findBetweenDatesByUser(String userUniqueName, Date startTimeDate, Date endTimeDate);
    
    /**
     *
     * @param reservationId
     * @return
     */
    public List<ReservationInstance> findByReservation(Integer reservationId);
    /**
     *
     * @param resource
     * @param startTimeDate
     * @param endTimeDate
     * @return
     */
    public Boolean isAvaliable(Resource resource, Date startTimeDate, Date endTimeDate);
    
}
