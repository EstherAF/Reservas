/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.controllers.controllerModel.LoginStatus;
import com.citius.reservas.models.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Transactional
public interface AccessBusiness {
   
    public User getLoggedUser(UserDetails details);
    
    @Transactional(readOnly = true)
    public String getUniqueNameOfLoggedUser();
    
    @Transactional(readOnly = true)
    public Boolean isAdmin();
    
    @Transactional(readOnly = true)
    public User findUser(String uniqueName);   

    @Transactional(readOnly = true)
    public User findUserFromDB(String uniqueName);   
    
    @Transactional(readOnly = true)
    public LoginStatus getLoginStatus();
    
    public LoginStatus login(String name, String password);
    
    //From DB
    @Transactional(readOnly = true)
    public List<User> getUsers();
}
