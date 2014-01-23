/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Resource;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
//@Service
@Transactional
public interface ResourceBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Resource> readAll();
    
    /**
     *
     * @param id
     * @return
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public Resource read(Integer id) throws UnknownResourceException;
    
    /**
     *
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public Resource readByName(String name);
    
    /**
     *
     * @param groupId
     * @param start
     * @param end
     * @return
     */
    @Transactional(readOnly=true)
    public List<Resource> readAvaliableByGroupBetweenDates(Integer groupId, Date start, Date end);
    
    /**
     *
     * @param resource
     * @param quantity
     * @return
     */
    public List<Resource> create(Resource resource, Integer quantity) throws UnknownResourceException;
    /**
     *
     * @param resource
     * @return
     */
    public Resource createOrSave(Resource resource) throws UnknownResourceException;
    /**
     *
     * @param id
     * @throws UnknownResourceException
     */
    public void delete(Integer id) throws UnknownResourceException;
}
