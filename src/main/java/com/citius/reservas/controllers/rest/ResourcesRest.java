package com.citius.reservas.controllers.rest;

import com.citius.reservas.models.Resource;
import com.citius.reservas.business.ResourceBusiness;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/resources")
//@Secured("IS_AUTHENTICATED_FULLY")
public class ResourcesRest {
    
    @Autowired
    private ResourceBusiness rs;
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody Resource read() {
        System.out.println("Read resources");
        Resource r = rs.readAll();
        return r;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Resource read(@PathVariable Integer id) {
        Resource r = rs.read(id);
        return r;
    }
    
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/resourceGroup", method = RequestMethod.POST)
    public void createResourceGroup(@RequestBody String name) {
            rs.createResourceGroup(name, 1);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/finalResource", method = RequestMethod.POST)
    public void createFinalResource(@RequestBody String name,  
                                    @RequestBody(required = false) String description,
                                    @RequestBody Integer quantity,
                                    @RequestBody(required = false) Integer group_id) {
        rs.createFinalResource(name, group_id, description, quantity);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/resourceGroup", method = RequestMethod.PUT)
    public void updateResourceGroup(@RequestBody Integer id,
                                    @RequestBody String name) {
        rs.saveResourceGroup(id, name, 1);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/finalResource", method = RequestMethod.PUT)
    public void updateFinalResource(@RequestBody Integer id,
                                    @RequestBody String name,  
                                    @RequestBody(required = false) String description,
                                    @RequestBody Integer quantity,
                                    @RequestBody(required = false) Integer group_id) {
        rs.saveFinalResource(id, name, group_id, description, quantity);
    }

    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        rs.delete(id);
    }
    
}
