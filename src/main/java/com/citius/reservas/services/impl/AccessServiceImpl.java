/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.services.impl;

import com.citius.reservas.models.Role;
import com.citius.reservas.models.User;
import com.citius.reservas.repositories.LdapRepository;
import com.citius.reservas.repositories.RoleRepository;
import com.citius.reservas.repositories.UserRepository;
import com.citius.reservas.services.AccessService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author esther
 */
@Service
public class AccessServiceImpl implements AccessService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LdapRepository ldapRepository;

    @Transactional
    @Override
    public User getLoggedUser(UserDetails details) {
        
        String userName = details.getUsername();
        
        //Search user
        User user = userRepository.findByUniqueName(userName);

        if (user == null) {
            //Get role
            GrantedAuthority ga = details.getAuthorities().iterator().next();
            String roleName = ga.getAuthority().toLowerCase().split("_")[1];
            Role rol = roleRepository.findByName(roleName);
            
            //Get user from LDAP
            List<User> list = ldapRepository.getByUID(userName);
            
            user = list.get(0);
            //Store new user in DB
            user.addRole(rol);
            user = userRepository.create(user);
            System.out.println(user.getUniqueName()+" created"); //LdC
        }

        return user;

    }
}
