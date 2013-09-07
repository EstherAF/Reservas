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
    @Transactional(readOnly = true)
    public List<ResourceGroup> readAll();
    
    @Transactional(readOnly = true)
    public ResourceGroup read(Integer id);
    
    @Transactional(readOnly = true)
    public ResourceGroup readByName(String name);
    
    public ResourceGroup create(ResourceGroup r);
    
    public ResourceGroup save(ResourceGroup r);
    
    public void delete(Integer id);
    
    public void deleteWithResources(Integer id) throws UnknownResourceException;
}
