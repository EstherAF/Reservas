/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.repositories.ReservationRepository;
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
    private ResourceGroupBusiness resourceGroupBusiness;
    @Autowired ReservationRepository reservationRepository;

    @Override
    public List<Resource> readAll() {

        List<Resource> l = resourceRepository.findAll();
        return l;
    }

    @Override
    public Resource read(Integer id) throws UnknownResourceException{
        Resource resource = resourceRepository.find(id);
        if(resource==null)
            throw new UnknownResourceException();
        
        return resource;
    }
    
    @Override
    public Resource createOrSave(Resource resource){
        
        //Check group
        String name = (resource.getGroup()==null)? "default" : resource.getGroup().getName();
        
        resource.setGroup(resourceGroupBusiness.readByName(name));
        resource = resourceRepository.save(resource);
        
        ResourceGroup group = resource.getGroup();
        
        if(! group.getResources().contains(resource)){
            group.getResources().add(resource);
            resourceGroupBusiness.save(group);
        }
        
        return resource;
    }
    
    @Override
    public List<Resource> create(Resource resource, Integer quantity){
        
        List<Resource> resources= new ArrayList<>();
        Resource iterator = new Resource(resource);
        
        String initName = resource.getName();
        String incrementalName;
        
        if(quantity == 1){
            Resource created = this.createOrSave(iterator);
            resources.add(created);
            resourceRepository.detach(iterator);
             
            return resources;
        }
        
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
    public void delete(Integer id) throws UnknownResourceException{
        Resource resource = this.resourceRepository.find(id);
        
        List<Reservation> l = this.reservationRepository.findByResource(new Resource(id, null, null));
        
        for(Reservation r: l){
            if(r.getResources().size()==1){
                this.reservationRepository.delete(r.getId());
            }else{
                r.getResources().remove(resource);
                this.reservationRepository.save(r);
            }
        }
        
        resourceRepository.delete(id);
    }

    @Override
    public List<Resource> readAvaliableByGroupBetweenDates(Integer groupId, Date start, Date end) {
        return resourceRepository.findAvaliableByGroupDates(groupId, start, end);
    }

    @Override
    public Resource readByName(String name) {
        return this.resourceRepository.findByName(name);
    }
}
