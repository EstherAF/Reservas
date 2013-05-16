/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import com.citius.reservas.models.User;
import java.util.List;

/**
 *
 * @author esther
 */
public interface UserRepository extends GenericRepository<User> {

    User findByUniqueName(String uniqueName);
    
    List<User> findByFullName(String fullName);

    List<User> findByEmail(String email);

    List<User> findAll();
}
