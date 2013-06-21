package com.citius.reservas.controllers;

import com.citius.reservas.models.Resource;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.controllers.helper.CustomJsonMapper;
import com.citius.reservas.models.ResourceGroup;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/resources")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class ResourceController {
      
    @Autowired
    private ResourceGroupBusiness rgb;
    @Autowired
    private ResourceBusiness rb;
    
    @Autowired
    private CustomJsonMapper mapper;
    
    private static final Logger logger = Logger.getLogger(ResourceController.class);
    
    
    /********************  JSON   *************************/
    @RequestMapping(value = "/", 
            method = RequestMethod.GET,
            produces="application/json")
    public @ResponseBody List<ResourceGroup> read() {
        System.out.println("Read resources");
        List<ResourceGroup> l = rgb.readAll();
        return l;
    }
    
    @RequestMapping(value = "/{id}", 
            method = RequestMethod.GET,
            produces="application/json")
    public @ResponseBody Resource read(@PathVariable Integer id) {
        Resource r = rb.read(id);
        return r;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", 
            method = RequestMethod.POST,
            produces="application/json")
    public Resource create(@RequestBody Resource resource) {
        Integer idGroup=1;
        if(resource.getGroup() != null)
            idGroup = resource.getGroup().getId();
        return rb.create(resource.getName(), idGroup, resource.getDescription());
    }
    
    @ResponseBody
    @RequestMapping(value = "/", 
            method = RequestMethod.PUT,
            produces="application/json")
    public Resource update(@RequestBody Integer id,
                        @RequestBody String name,  
                        @RequestBody(required = false) String description,
                        @RequestBody(required = false) Integer groupId) {
        return rb.save(id, name, groupId, description);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", 
            method = RequestMethod.DELETE,
            produces="application/json")
    public void delete(@PathVariable Integer id) {
        rb.delete(id);
    }
    
    /********************  HTML   *************************/
    
    @RequestMapping(value="/", produces = "text/html", method = RequestMethod.GET)
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String resources(Model model) {
        List<ResourceGroup> l = rgb.readAll();
        logger.debug("Found");
        String json = null;
        try {
            json = mapper.writeValueAsString(l);
        } catch (JsonGenerationException ex) {
            logger.error(ex, ex);
        } catch (JsonMappingException ex) {
            logger.error(ex, ex);
        } catch (IOException ex) {
            logger.error(ex, ex);
        }
        logger.debug("resources:"+json);
        model.addAttribute("resourcesJson", json);
        model.addAttribute("resources", l);
        return "resources";
    }
    
}
