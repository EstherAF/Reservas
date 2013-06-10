/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.ResourceGroupRepository;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther
 */
@Repository
public class ResourceGroupRepositoryImpl extends GenericRepositoryImpl<ResourceGroup> implements ResourceGroupRepository{
    private static final Logger logger = Logger.getLogger(ResourceGroupRepositoryImpl.class);

    @Override
    public ResourceGroup findByName(String name) {
        Query q = this.em.createNamedQuery("Role.findByName");
        q.setParameter("name", name);
        return this.singleQuery(q);
    }
    
    

}
