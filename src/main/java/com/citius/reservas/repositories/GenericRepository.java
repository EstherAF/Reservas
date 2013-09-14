/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import java.util.List;

/**
 *
 * @param <T> 
 * @author esther
 */
public interface GenericRepository<T> {
    
    /**
     *
     * @param pk
     * @return
     */
    public T find(Object pk);
    
    /**
     *
     * @return
     */
    public List<T> findAll();
    
    /**
     *
     * @param t
     * @return
     */
    public T save(T t);
    
    /**
     *
     * @param t
     * @return
     */
    public T create(T t);
    
    /**
     *
     * @param id
     */
    public void delete(Object id);
    
    /**
     *
     * @param t
     */
    public void detach(T t);
    
    /**
     *
     */
    public void flush();
    
    /**
     *
     * @param t
     * @return
     */
    public boolean isInContext(T t);

}
