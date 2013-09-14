/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.controllers.controllerModel.LoginStatus;
import com.citius.reservas.exceptions.UnknownResourceException;
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
   
    /**
     * 
     * @param details
     * @return 
     */
    public User getLoggedUser(UserDetails details);
    
    /**
     * 
     * @return 
     */
    @Transactional(readOnly = true)
    public String getUniqueNameOfLoggedUser();
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Boolean isAdmin();
    
    /**
     *
     * @param uniqueName
     * @return
     */
    @Transactional(readOnly = true)
    public User findUser(String uniqueName);   

    /**
     *
     * @param uniqueName
     * @return
     * @throws UnknownResourceException
     */
    @Transactional(readOnly = true)
    public User findUserFromDB(String uniqueName) throws UnknownResourceException;   
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public LoginStatus getLoginStatus();
    
    /**
     *
     * @param name
     * @param password
     * @return
     */
    public LoginStatus login(String name, String password);
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> getUsers();
}
