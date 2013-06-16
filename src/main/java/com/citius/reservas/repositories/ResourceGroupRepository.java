/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.ResourceGroup;

/**
 *
 * @author esther
 */
public interface ResourceGroupRepository extends GenericRepository<ResourceGroup>{
    
    public ResourceGroup findByName(String name);
    
}
