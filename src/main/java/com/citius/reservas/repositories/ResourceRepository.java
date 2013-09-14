/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author esther
 */
public interface ResourceRepository extends GenericRepository<Resource>{
    
    /**
     *
     * @param name
     * @return
     */
    public Resource findByName(String name);
    /**
     *
     * @param group_name
     * @return
     */
    public List<Resource> findByGroup(String group_name);
    /**
     *
     * @return
     */
    public List<Resource> findWithoutGroup();
    /**
     *
     * @param groupId
     * @param start
     * @param end
     * @return
     */
    public List<Resource> findAvaliableByGroupDates(Integer groupId, Date start, Date end);
    
}
