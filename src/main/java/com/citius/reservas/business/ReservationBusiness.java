/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.controllers.controllerModel.ReservationCustom;
import com.citius.reservas.controllers.controllerModel.ResourceGroupCustom;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Transactional
public interface ReservationBusiness {
        
    /**
     *
     * @param reservation_id
     * @return
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public Reservation read(Integer reservation_id) throws UnknownResourceException;
    
    /**
     *
     * @param mapper
     * @param reservation_id
     * @return
     * @throws IOException
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public String readAsJson(ObjectMapper mapper, Integer reservation_id) 
            throws IOException, UnknownResourceException;
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readAll();
    
    /**
     *
     * @param week
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeek(Calendar week);
    
    /**
     *
     * @param ownerUniqueName
     * @param week
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByUser(String ownerUniqueName, 
            Calendar week);
    
    /**
     *
     * @param resourceId
     * @param week
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByResource(Integer resourceId, 
            Calendar week);
    
    /**
     *
     * @param resourceGroupId
     * @param week
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByWeekByResourceGroup(
            Integer resourceGroupId, Calendar week);
    
    /**
     *
     * @param month
     * @param year
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonth(Integer month, Integer year);
    
    /**
     *
     * @param ownerUniqueName
     * @param month
     * @param year
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByUser(String ownerUniqueName, 
            Integer month, Integer year);
    
    /**
     *
     * @param resourceId
     * @param month
     * @param year
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByResource(Integer resourceId, 
            Integer month, Integer year);
    
    /**
     *
     * @param resourceGroupId
     * @param month
     * @param year
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationInstance> readByMonthByResourceGroup(
            Integer resourceGroupId, Integer month, Integer year);

    /**
     *
     * @param reservation
     * @param groups
     * @return
     * @throws NotAvaliableException
     * @throws NotPossibleInstancesException
     * @throws UnknownResourceException
     */
    public Reservation saveReservation(Reservation reservation, 
            List<ResourceGroupCustom> groups) 
            throws NotAvaliableException, NotPossibleInstancesException, 
            UnknownResourceException;
    
    /**
     *
     * @param reservation
     * @param groups
     * @return
     * @throws NotAvaliableException
     * @throws NotPossibleInstancesException
     * @throws UnknownResourceException
     */
    @Transactional(rollbackFor = {Throwable.class})
    public Reservation updateReservation(Reservation reservation, 
            List<ResourceGroupCustom> groups) 
            throws NotAvaliableException, NotPossibleInstancesException, 
            UnknownResourceException;
    
    /**
     *
     * @param id
     */
    public void deleteReservation(Integer id);
    
    /**
     *
     * @param reservationId
     * @param ownerUniqueName
     * @return
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public Boolean isOwner(Integer reservationId, String ownerUniqueName) 
            throws UnknownResourceException;
    
    /**
     *
     * @param reservationId
     * @param uniqueName
     * @return
     * @throws UnknownResourceException
     */
    public Boolean canEdit(Integer reservationId, String uniqueName) 
            throws UnknownResourceException;
    
    /**
     *
     * @param resource
     * @return
     * @throws UnknownResourceException
     */
    public List<Reservation> findByResource(Resource resource) throws UnknownResourceException;
    /**
     *
     * @param resource
     * @return
     * @throws UnknownResourceException
     */
    public List<Reservation> findByResourceGroup(ResourceGroup resource) throws UnknownResourceException;
    
}
