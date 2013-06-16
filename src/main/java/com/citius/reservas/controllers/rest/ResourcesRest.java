package com.citius.reservas.controllers.rest;

import com.citius.reservas.models.Resource;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    
    @Autowired
    private ResourceGroupBusiness rgs;
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody List<ResourceGroup> read() {
        System.out.println("Read resources");
        List<ResourceGroup> l = rgs.readAll();
        return l;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Resource read(@PathVariable Integer id) {
        Resource r = rs.read(id);
        return r;
    }
    

    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Resource create(@RequestBody String name,  
                        @RequestBody(required = false) String description,
                        @RequestBody(required = false) Integer groupId) {
        return rs.create(name, groupId, description);
    }
    
    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Resource update(@RequestBody Integer id,
                        @RequestBody String name,  
                        @RequestBody(required = false) String description,
                        @RequestBody(required = false) Integer groupId) {
        return rs.save(id, name, groupId, description);
    }

    //@Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        rs.delete(id);
    }
    
}
