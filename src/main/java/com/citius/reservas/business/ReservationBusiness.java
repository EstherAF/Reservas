/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.controllers.customModel.ReservationCustom;
import com.citius.reservas.controllers.customModel.ResourceGroupCustom;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.Calendar;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Ã?lvarez Feijoo
 */
@Transactional
public interface ReservationBusiness {
        
    @Transactional(readOnly = true)
    public Reservation read(Integer reservation_id);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readAll();
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeek(Calendar week);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByUser(String ownerUniqueName, 
            Calendar week);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByResource(Integer resourceId, 
            Calendar week);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByResourceGroup(
            Integer resourceGroupId, Calendar week);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonth(Integer month, Integer year);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByUser(String ownerUniqueName, 
            Integer month, Integer year);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByResource(Integer resourceId, 
            Integer month, Integer year);
    
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByResourceGroup(
            Integer resourceGroupId, Integer month, Integer year);

    public Reservation saveReservation(Reservation reservation, 
            List<ResourceGroupCustom> groups) 
            throws NotAvaliableException, NotPossibleInstancesException;
    
    public Reservation updateReservation(Reservation reservation, 
            List<ResourceGroupCustom> groups) 
            throws NotAvaliableException, NotPossibleInstancesException, 
            UnknownResourceException;
    
    public void deleteReservation(Integer id);
    
    public Boolean isOwner(Integer reservationId, String ownerUniqueName);
    
    public Boolean canEdit(Integer reservationId, String uniqueName);
    
    
    
}
