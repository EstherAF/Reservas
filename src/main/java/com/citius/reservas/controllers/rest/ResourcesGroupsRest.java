package com.citius.reservas.controllers.rest;

import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.models.ResourceGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value="/resources/groups",
        produces="application/json")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class ResourcesGroupsRest {
    @Autowired
    private ResourceGroupBusiness rgs;
    
    @ResponseBody 
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResourceGroup read(@PathVariable Integer id) {
        return rgs.read(id);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResourceGroup create(@RequestBody ResourceGroup resourceGroup) {
         return rgs.create(resourceGroup);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResourceGroup update(@RequestBody Integer id,
                                    @RequestBody String name) {
        return rgs.save(id, name);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOnlyGroup(@PathVariable Integer id) {
        rgs.delete(id);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}/all", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        rgs.deleteWithResources(id);
    }
    
}
