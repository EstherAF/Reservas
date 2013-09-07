/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import java.util.List;

/**
 *
 * @author esther
 */
public interface GenericRepository<T> {
    
    public T find(Object pk);
    
    public List<T> findAll();
    
    public T save(T t);
    
    public T create(T t);
    
    public void delete(Object id);
    
    public void detach(T t);
    
    public void flush();
    
    public boolean isInContext(T t);

}
