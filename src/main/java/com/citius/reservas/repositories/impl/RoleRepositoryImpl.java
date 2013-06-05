/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.Role;
import com.citius.reservas.repositories.RoleRepository;
import com.citius.reservas.repositories.UserRepository;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role> 
    implements RoleRepository{
    
    private static final Logger logger = Logger.getLogger(RoleRepository.class);
    
    @Override
    public Role findByName(String name) {
        Query q = this.em.createNamedQuery("Role.findByName");
        q.setParameter("name", name);
        return this.singleQuery(q);
    }
}
