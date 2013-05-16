/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.Role;
import com.citius.reservas.repositories.RoleRepository;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Role> 
    implements RoleRepository{
    
    @Override
    public Role findByName(String name) {
        Query q = (Query) this.em.createNamedQuery("Role.findByName");
        q.setParameter("name", name);
        return this.singleQuery(q);
    }

    @Override
    public List<Role> findAll() {
        Query q = (Query) this.em.createNamedQuery("Role.findAll");
        return this.listQuery(q);
    }
}
