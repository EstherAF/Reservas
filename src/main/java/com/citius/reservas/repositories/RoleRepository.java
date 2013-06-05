/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Role;

/**
 *
 * @author esther
 */
public interface RoleRepository extends GenericRepository<Role>{
    
    Role findByName(String name);
}
