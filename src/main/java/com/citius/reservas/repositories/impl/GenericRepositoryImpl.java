/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.repositories.GenericRepository;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author esther
 */
public class GenericRepositoryImpl<T> implements GenericRepository<T> {

    @PersistenceContext
    protected EntityManager em;
    
    
    protected Class<T> type;

    /**
     *
     */
    
    public GenericRepositoryImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
        
        //em=emf.createEntityManager();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    

    @Override
    public T find(Object pk) {
        try {
            return em.find(type, pk);
        } catch (java.util.NoSuchElementException ex) {
            return null;
        }
    }

    @Override
    public T create(T t) {
        em.persist(t);
        em.flush();
        em.refresh(t);
        return t;
    }
    
    @Override
    public T save(T t) {
        t = em.merge(t);
        em.flush();
        return t;
    }

    @Override
    public void delete(Object id) {
        if (id.getClass().equals(this.type)) {
            em.remove(id);
        } else {
            T ref = em.getReference(type, id);
            em.remove(ref);
        }
        
        em.flush();
    }

    @Override
    public List<T> listQuery(Query q) {
        List<T> list = q.getResultList();
        if (list == null) {
            list = new ArrayList<T>();
        }
        return list;
    }
    
    @Override
    public T singleQuery(Query q) {
        T t;
        try{
            t= (T) q.getSingleResult();
        }catch(NoResultException ex){
            t=null;
        }
        return t;
    }

    @Override
    @Transactional
    public List<T> findAll() {
        Query q = em.createNamedQuery(type.getSimpleName()+".findAll");
        return listQuery(q);
    }
}
