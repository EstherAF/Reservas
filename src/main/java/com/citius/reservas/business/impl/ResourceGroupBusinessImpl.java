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
public class ResourceGroupBusinessImpl implements ResourceGroupBusiness {

    @Autowired
    private ResourceGroupRepository resourceGroupRepository;
    
    @Autowired
    private ResourceBusiness resourceBusiness;

    @Transactional
    @Override
    public List<ResourceGroup> readAll() {

        List<ResourceGroup> l = resourceGroupRepository.findAll();
        return l;
    }

    @Transactional
    @Override
    public ResourceGroup read(Integer id) {
        return resourceGroupRepository.find(id);
    }

    @Transactional
    @Override
    public ResourceGroup create(ResourceGroup r) {

        r = resourceGroupRepository.save(r);

        return r;
    }

    @Transactional
    @Override
    public ResourceGroup save(Integer id, String name) {

        ResourceGroup group = null;

        if (id == null)
            group = new ResourceGroup(name);
        
        else if (id <= 0)
            throw new IllegalArgumentException("id " + id + " <=0");
        
        else {
            group = resourceGroupRepository.find(id);

            if (group == null) 
                throw new IllegalArgumentException(id + " can't be found");
            else 
                group.setName(name);
        }

        group = resourceGroupRepository.save(group);

        return group;
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        ResourceGroup g = resourceGroupRepository.find(id);
        if (g == null) {
            throw new IllegalArgumentException(id + " can't be found");
        } else {
            ResourceGroup def = resourceGroupRepository.findByName("default");
            for (Resource r : g.getResources()) {
                r.setGroup(def);
            }
        }
        resourceGroupRepository.delete(id);
    }

    @Transactional
    @Override
    public void deleteWithResources(Integer id) {
        ResourceGroup g = resourceGroupRepository.find(id);
        if (g == null) {
            throw new IllegalArgumentException(id + " can't be found");
        } else {
            for (Resource r : g.getResources()) {
                resourceBusiness.delete(r.getId());
            }
        }
        
        resourceGroupRepository.delete(id);
    }
}
