/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.*;
import java.util.List;

/**
 *
 * @author esther
 */
public interface GenericResourceRepository<T> extends GenericRepository<T>{

    
    public T findByName(String name);
    public List<T> findByParent(ResourceGroup parent);
    public T findPath();

}
