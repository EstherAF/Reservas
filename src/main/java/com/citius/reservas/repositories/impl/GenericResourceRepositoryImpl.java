/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import javax.persistence.Query;
import com.citius.reservas.repositories.GenericResourceRepository;
import com.citius.reservas.repositories.impl.resources.FinalResourceRepositoryImpl;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author esther
 */
public class GenericResourceRepositoryImpl<T> extends GenericRepositoryImpl<T> implements GenericResourceRepository<T> {

    private static final Logger logger = Logger.getLogger(GenericRepositoryImpl.class);
    
    @Override
    public T findByName(String name) {
        Query q = em.createNamedQuery(type.getSimpleName() + ".findByName");
        q.setParameter("name", name);
        return this.singleQuery(q);
    }

    @Override
    public List<T> findByParent(ResourceGroup parent) {
        Query q = em.createNamedQuery(type.getSimpleName() + ".findByParent");
        q.setParameter("parent", parent);
        return this.listQuery(q);
    }

    @Override
    public T findPath() {
        Query q = em.createNamedQuery(type.getSimpleName() + ".findWithoutParent");
        //return this.singleQuery(q);
        T t;
        try{
            t= (T) q.getSingleResult();
        }catch(NoResultException ex){
            t=null;
        }
        
        return t;
    }
    
    
}
