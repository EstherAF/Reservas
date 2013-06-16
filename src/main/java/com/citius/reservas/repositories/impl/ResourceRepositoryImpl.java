/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.ResourceRepository;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther
 */
@Repository
public class ResourceRepositoryImpl extends GenericRepositoryImpl<Resource> implements ResourceRepository{
    private static final Logger logger = Logger.getLogger(ResourceRepositoryImpl.class);

    @Override
    public Resource findByName(String name) {
        Query q = this.em.createNamedQuery("Resource.findByName");
        q.setParameter("name", name);
        return this.singleQuery(q);
    }

    @Override
    public List<Resource> findByGroup(String group_name) {
        Query q = this.em.createNamedQuery("Resource.findByGroup");
        q.setParameter("group_name", group_name);
        return this.listQuery(q);
    }

    @Override
    public List<Resource> findWithoutGroup() {
        Query q = this.em.createNamedQuery("Resource.findWithoutGroup");
        return this.listQuery(q);
    }
}
