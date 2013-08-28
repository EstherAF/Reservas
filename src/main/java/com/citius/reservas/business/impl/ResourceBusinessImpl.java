/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.repositories.ResourceGroupRepository;
import com.citius.reservas.repositories.ResourceRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Resource> readAll() {

        List<Resource> l = resourceRepository.findAll();
        return l;
    }

    @Override
    public Resource read(Integer id) {
        return resourceRepository.find(id);
    }
    
    @Override
    public Resource createOrSave(Resource resource){
        
        //Check group
        if(resource.getGroup()==null)
            resource.setGroup(resourceGroupRepository.findByName("default"));
        
        resource = resourceRepository.save(resource);
        
        ResourceGroup group = resource.getGroup();
        
        if(! group.getResources().contains(resource)){
            group.getResources().add(resource);
            resourceGroupRepository.save(group);
        }
        
        return resource;
    }
    
    @Override
    public List<Resource> create(Resource resource, Integer quantity){
        
        List<Resource> resources= new ArrayList<>();
        Resource iterator = new Resource(resource);
        
        String initName = resource.getName();
        String incrementalName;
        
        for(int i=0; i<quantity; i++){
            incrementalName = initName + " ("+(i+1)+")";
            iterator.setName(incrementalName);
            
            Resource created = this.createOrSave(iterator);
            resources.add(created);
            resourceRepository.detach(iterator);
        }
        
        return resources;
    }
    
    @Override
    public void delete(Integer id) {
        resourceRepository.delete(id);
    }

    @Override
    public List<Resource> readAvaliableByGroupBetweenDates(Integer groupId, Date start, Date end) {
        return resourceRepository.findAvaliableByGroupDates(groupId, start, end);
    }
}
