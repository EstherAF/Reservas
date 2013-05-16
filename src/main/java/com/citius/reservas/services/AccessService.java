/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.services;

import com.citius.reservas.models.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Esther
 */
public interface AccessService {
    User getLoggedUser(UserDetails details);   
}
