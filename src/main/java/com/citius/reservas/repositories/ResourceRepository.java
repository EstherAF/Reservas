/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Resource;
import java.util.List;

/**
 *
 * @author esther
 */
public interface ResourceRepository extends GenericRepository<Resource>{
    
    public Resource findByName(String name);
    public List<Resource> findByGroup(String group_name);
    public List<Resource> findWithoutGroup();
    
}
