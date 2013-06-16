/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.User;
import com.citius.reservas.repositories.LdapRepository;
import com.citius.reservas.repositories.UserRepository;
import com.citius.reservas.business.AccessBusiness;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author esther
 */
@Service
public class AccessBusinessImpl implements AccessBusiness {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LdapRepository ldapRepository;

    @Transactional
    @Override
    public User logInUser(UserDetails details) {

        String userName = details.getUsername();

        //Search user
        User user = userRepository.findByUniqueName(userName);

        //If doesn't exist
        if (user == null) {

            //Get user from LDAP
            List<User> list = ldapRepository.getByUID(userName);
            user = list.get(0);

            //Get role from LDAP
            GrantedAuthority ga = details.getAuthorities().iterator().next();
            String roleName = ga.getAuthority().toLowerCase().split("_")[1];
            user.setRole(roleName);

            //Store in DB
            user = userRepository.create(user);
            System.out.println(user.getUniqueName() + " created"); //LdC
        }

        return user;
    }

    @Override
    @Transactional
    public User findUser(String uniqueName) {
        User user = userRepository.findByUniqueName(uniqueName);
        System.out.println("Found user " + user.getUniqueName());
        return user;
    }

    @Override
    public String getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) 
            return authentication.getName();
        else 
            return null;
    }
}
