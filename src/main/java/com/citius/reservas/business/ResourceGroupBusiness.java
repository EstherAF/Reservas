/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther
 */
@Service
public interface ResourceGroupBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    public List<ResourceGroup> readAll();
    public ResourceGroup read(Integer id);
    public ResourceGroup create(String name);
    public ResourceGroup save(Integer id, String name);
    public void delete(Integer id);
    public void deleteWithResources(Integer id);
}
