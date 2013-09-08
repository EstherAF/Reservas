/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Resource;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
//@Service
@Transactional
public interface ResourceBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    @Transactional(readOnly = true)
    public List<Resource> readAll();
    
    @Transactional(readOnly = true)
    public Resource read(Integer id) throws UnknownResourceException;
    
    @Transactional(readOnly = true)
    public Resource readByName(String name);
    
    @Transactional(readOnly=true)
    public List<Resource> readAvaliableByGroupBetweenDates(Integer groupId, Date start, Date end);
    
    public List<Resource> create(Resource resource, Integer quantity);
    public Resource createOrSave(Resource resource);
    public void delete(Integer id) throws UnknownResourceException;
}
