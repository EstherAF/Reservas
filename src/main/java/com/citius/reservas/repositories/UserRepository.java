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

    /**
     *
     * @param uniqueName
     * @return
     */
    User findByUniqueName(String uniqueName);
    
    /**
     *
     * @param fullName
     * @return
     */
    List<User> findByFullName(String fullName);

    /**
     *
     * @param email
     * @return
     */
    List<User> findByEmail(String email);
}
