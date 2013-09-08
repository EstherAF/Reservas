/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.User;
import com.citius.reservas.repositories.LdapRepository;
import com.citius.reservas.repositories.UserRepository;
import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.controllerModel.LoginStatus;
import com.citius.reservas.exceptions.UnknownResourceException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Service
public class AccessBusinessImpl implements AccessBusiness {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LdapRepository ldapRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static final Logger logger = Logger.getLogger(AccessBusiness.class);

    @Override
    public User getLoggedUser(UserDetails details) {

        if(details == null)
            details = (LdapUserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(details == null)
            return null;
        
        String userName = details.getUsername();
        User user = userRepository.findByUniqueName(userName);

        //If doesn't exist
        if (user == null) {

            //Get user and role from LDAP
            List<User> list = ldapRepository.getByUID(userName);
            user = list.get(0);
            String roleName = "user";

            for (GrantedAuthority ga : details.getAuthorities()) {
                roleName = ga.getAuthority().toLowerCase().split("_")[1];
                if (roleName.equals("tic")) {
                    break;
                }
            }
            user.setRole(roleName);

            //Store in DB
            user = userRepository.save(user);
            logger.debug("User '" + user.getUniqueName() + "' created on DB");
        }
        return user;
    }

    @Override
    public User findUser(String uniqueName) {
        logger.debug("Find user '" + uniqueName + "'");

        List<User> l = ldapRepository.getByUID(uniqueName);

        logger.debug("Found " + l.size() + " results");
        if(l.isEmpty())
            return null;
        return l.get(0);
    }

    @Override
    public String getUniqueNameOfLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    @Override
    public Boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        String roleName="";
        for (GrantedAuthority ga : auth.getAuthorities()) {
                roleName = ga.getAuthority().toLowerCase().split("_")[1];
                if (roleName.equals("tic")) {
                    return true;
                }
            }
        
        return false;
    }

    @Override
    public LoginStatus getLoginStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) auth.getPrincipal();
            User user = this.getLoggedUser(userDetails);
            return new LoginStatus(true, user.getUniqueName(), user.getRole());
        } else {
            return new LoginStatus(false, null, null);
        }
    }

    @Override
    public LoginStatus login(String name, String password) {
        //Username - password pair
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, password);

        try {
            //Validation of pair
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            //Log against the DB. If doesn't exist, will be created
            LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) auth.getPrincipal();
            User user = this.getLoggedUser(userDetails);

            return new LoginStatus(auth.isAuthenticated(), user.getUniqueName(), user.getRole());
            
        } catch (BadCredentialsException e) {
            
            return new LoginStatus(false, null, null);
            
        }
    }
    
    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public User findUserFromDB(String uniqueName) throws UnknownResourceException {
        User user = this.userRepository.findByUniqueName(uniqueName);
        if(user==null)
            throw new UnknownResourceException();
                
        return user;
    }
}
