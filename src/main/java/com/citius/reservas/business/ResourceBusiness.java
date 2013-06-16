/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business;

import com.citius.reservas.models.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther
 */
@Service
public interface ResourceBusiness {
    
    /* Lista ordenada de recursos (finales y grupos)*/
    public List<Resource> readAll();
    public Resource read(Integer id);
    public Resource create(String name, Integer groupId, String description);
    public Resource save(Integer id, String name, Integer groupId, String description);
    public void delete(Integer id);
}
