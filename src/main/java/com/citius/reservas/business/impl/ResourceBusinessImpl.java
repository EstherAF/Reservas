/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.repositories.ResourceGroupRepository;
import com.citius.reservas.repositories.ResourceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
@Service
public class ResourceBusinessImpl implements ResourceBusiness {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ResourceGroupRepository resourceGroupRepository;
    @Autowired
    private ResourceGroupBusiness resourceGroupBusiness;

    @Transactional
    @Override
    public List<Resource> readAll() {

        List<Resource> l = resourceRepository.findAll();
        return l;
    }

    @Transactional
    @Override
    public Resource read(Integer id) {
        return resourceRepository.find(id);
    }
    
    @Transactional
    @Override
    public Resource create(String name, Integer group_id, String description){
        
        Resource r = this.save(null, name, group_id, description);
        
        return r;
    }
    
    @Transactional
    @Override
    public Resource save(Integer id, String name, Integer group_id, 
        String description){
        
        ResourceGroup group;
        
        //Check group
        if(group_id!=null){
            group = resourceGroupRepository.find(group_id);
            if(group==null)
                throw new IllegalArgumentException("Group "+id+" can't be found");
        }
        else
            group = resourceGroupRepository.findByName("default");
        
        
        
        Resource r = new Resource(id, name, group, description);
        
        r = resourceRepository.save(r);
        
        group.getResources().add(r);
        resourceGroupRepository.save(group);
        
        return r;
    }
    
    @Transactional
    @Override
    public void delete(Integer id) {
        resourceRepository.delete(id);
    }
}
