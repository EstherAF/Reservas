/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.User;
import com.citius.reservas.repositories.UserRepository;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepository.class);
    
    @Override
    public User findByUniqueName(String uniqueName) {
        Query q = (Query) this.em.createNamedQuery("User.findByUniqueName", User.class);
        q.setParameter("uniqueName", uniqueName);    
        return this.singleQuery(q);
    }
    
    @Override
    public List<User> findByFullName(String fullName) {
        Query q = (Query) this.em.createNamedQuery("User.findByFullName");
        q.setParameter("fullName", fullName);
        return this.listQuery(q);
    }

    @Override
    public List<User> findByEmail(String email) {
        Query q = (Query) this.em.createNamedQuery("User.findByEmail");
        q.setParameter("email", email);
        return this.listQuery(q);
    }
}
