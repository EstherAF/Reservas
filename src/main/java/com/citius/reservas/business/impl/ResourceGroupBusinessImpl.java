/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.repositories.ResourceGroupRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Esther
 */
@Service
public class ResourceGroupBusinessImpl implements ResourceGroupBusiness {

    @Autowired
    private ResourceGroupRepository resourceGroupRepository;
    @Autowired
    private ResourceBusiness resourceBusiness;
    
    @Override
    public List<ResourceGroup> readAll() {

        List<ResourceGroup> l = resourceGroupRepository.findAll();
        return l;
    }


    @Override
    public ResourceGroup read(Integer id) throws UnknownResourceException{
        ResourceGroup group = resourceGroupRepository.find(id);
        if(group == null)
            throw new UnknownResourceException();
        return group;
    }


    @Override
    public ResourceGroup create(ResourceGroup r) {

        r = resourceGroupRepository.save(r);

        return r;
    }


    @Override
    public ResourceGroup save(ResourceGroup resourceGroup) {

        resourceGroup = resourceGroupRepository.save(resourceGroup);

        return resourceGroup;
    }


    @Override
    public void delete(Integer id) throws UnknownResourceException{
        ResourceGroup g = resourceGroupRepository.find(id);
        if (g == null) {
            throw new UnknownResourceException();
        } else {
            ResourceGroup def = resourceGroupRepository.findByName("default");
            for (Resource r : g.getResources()) {
                r.setGroup(def);
            }
        }
        resourceGroupRepository.delete(id);
    }


    @Override
    public void deleteWithResources(Integer id) throws UnknownResourceException{
        ResourceGroup g = resourceGroupRepository.find(id);
        if (g == null) {
            throw new UnknownResourceException();
        } else {
            for (Resource r : g.getResources()) {
                resourceBusiness.delete(r.getId());
            }
        }
        
        resourceGroupRepository.delete(id);
    }

    @Override
    public ResourceGroup readByName(String name) {
        return this.resourceGroupRepository.findByName(name);
    }
}
