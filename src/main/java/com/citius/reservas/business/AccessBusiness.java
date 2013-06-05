/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.models.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Esther
 */
public interface AccessBusiness {
    User getLoggedUser(UserDetails details);   
    
    User findUser(String uniqueName);   
}
