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
 * @author Esther Ã?lvarez Feijoo
 */
public interface ReservationInstanceRepository extends GenericRepository<ReservationInstance> {
    
    public List<ReservationInstance> findAllBetweenDates(Date startTimeDate, Date endTimeDate);
    public List<ReservationInstance> findAllBetweenDatesByResource(Date startTimeDate, Date endTimeDate, Integer resourceId);
    public List<ReservationInstance> findAllBetweenDatesByResourceGroup(Date startTimeDate, Date endTimeDate, Integer resourceGroupId);
    
    public List<ReservationInstance> findByDayByUser(String ownerUniqueName, Date day);
    public List<ReservationInstance> findBetweenDatesByUser(String userUniqueName, Date startTimeDate, Date endTimeDate);
    
    public List<ReservationInstance> findByReservation(Integer reservationId);
    public Boolean isAvaliable(Resource resource, Date startTimeDate, Date endTimeDate);
    
}
