/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.Role;
import com.citius.reservas.models.User;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author esther
 */
public interface RoleRepository extends GenericRepository<Role>{
    
    Role findByName(String name);
    
    List<Role> findAll();
    
}
