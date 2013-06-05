/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.FinalResource;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.GenericResourceRepository;
import com.citius.reservas.business.ResourceBusiness;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Esther
 */
@Service
public class ResourceBusinessImpl implements ResourceBusiness {

    @Autowired
    @Qualifier("resourceRepository")
    private GenericResourceRepository<Resource> resourceRepository;
    @Autowired
    @Qualifier("finalResourceRepository")
    private GenericResourceRepository<FinalResource> finalResourceRepository;
    @Autowired
    @Qualifier("resourceGroupRepository")
    private GenericResourceRepository<ResourceGroup> resourceGroupRepository;

    /*Cambiar de sitio¿?*/
    private void addSelf(List<Resource> list, Resource r) {
        list.add(r);
        if (r.getClass().equals(ResourceGroup.class)) {
            for (Resource child : ((ResourceGroup) r).getChilds()) {
                addSelf(list, child);
            }
        }
    }

    @Transactional
    @Override
    public Resource readAll() {

        Resource r = resourceRepository.findPath();
        return r;
    }

    @Transactional
    @Override
    public Resource read(Integer id) {
        return resourceRepository.find(id);
    }
    
    @Transactional
    @Override
    public Resource createResourceGroup(String name, Integer parent_id){
        
        ResourceGroup parent;
        if(parent_id!=null)
            parent = resourceGroupRepository.find(parent_id);
        else
            parent = resourceGroupRepository.findPath();
        
        ResourceGroup r = new ResourceGroup(name, parent);

        r = resourceGroupRepository.save(r);
        
        return r;
    }
    
    @Transactional
    @Override
    public Resource createFinalResource(String name, Integer parent_id, String description, Integer quantity){
        
        ResourceGroup parent;
        
        if(parent_id!=null)
            parent = resourceGroupRepository.find(parent_id);
        else
            parent = resourceGroupRepository.findPath();
        
        FinalResource r = new FinalResource(name, parent, description, quantity);

        r = finalResourceRepository.create(r);
        
        return r;
    }

    @Transactional
    @Override
    public Resource saveResourceGroup(Integer id, String name, Integer parent_id){
        
        ResourceGroup parent;
        if(parent_id!=null)
            parent = resourceGroupRepository.find(parent_id);
        else
            parent = resourceGroupRepository.findPath();
        
        ResourceGroup r = new ResourceGroup(id, name, parent);

        r = resourceGroupRepository.save(r);
        
        return r;
    }
    
    @Transactional
    @Override
    public Resource saveFinalResource(Integer id, String name, Integer parent_id, String description, Integer quantity){
        
        ResourceGroup parent;
        
        if(parent_id!=null)
            parent = resourceGroupRepository.find(parent_id);
        else
            parent = resourceGroupRepository.findPath();
        
        FinalResource r = new FinalResource(id, name, parent, description, quantity);

        r = finalResourceRepository.save(r);
        
        return r;
    }
    

    @Override
    public void delete(Integer id) {
        resourceRepository.delete(id);
    }
}
