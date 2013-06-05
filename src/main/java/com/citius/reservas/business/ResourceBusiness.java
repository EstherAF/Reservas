/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.models.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther
 */
@Service
public interface ResourceBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    public Resource readAll();
    
    public Resource read(Integer id);
    
    public Resource createResourceGroup(String name, Integer parent_id);
    
    public Resource saveResourceGroup(Integer id, String name, Integer parent_id);
    
    public Resource createFinalResource(String name, Integer parent_id, String description, Integer quantity);
    
    public Resource saveFinalResource(Integer id, String name, Integer parent_id, String description, Integer quantity);
    
    public void delete(Integer id);
}
