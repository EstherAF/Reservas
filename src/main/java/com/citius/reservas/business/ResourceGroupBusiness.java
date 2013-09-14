/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
@Service
@Transactional
public interface ResourceGroupBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<ResourceGroup> readAll();
    
    /**
     *
     * @param id
     * @return
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public ResourceGroup read(Integer id) throws UnknownResourceException;
    
    /**
     *
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public ResourceGroup readByName(String name);
    
    /**
     *
     * @param r
     * @return
     */
    public ResourceGroup create(ResourceGroup r);
    
    /**
     *
     * @param r
     * @return
     */
    public ResourceGroup save(ResourceGroup r);
    
    /**
     *
     * @param id
     * @throws UnknownResourceException
     */
    public void delete(Integer id) throws UnknownResourceException;
    
    /**
     *
     * @param id
     * @throws UnknownResourceException
     */
    public void deleteWithResources(Integer id) throws UnknownResourceException;
}
