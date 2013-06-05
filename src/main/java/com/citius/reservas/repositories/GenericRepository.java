/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories;

import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author esther
 */
public interface GenericRepository<T> {
    
    public T find(Object pk);
    
    public List<T> findAll();
    
    /* Returns the found entity list. 
     * If there are no results, the returned list is empty*/
    public List<T> listQuery(Query query);
    
    /* Queries by unique property.
     * Returns only one found entity. 
     * If there are no results, returns null*/
    public T singleQuery(Query query);
    
    public T save(T t);
    
    public T create(T t);
    
    public void delete(Object id);

}
