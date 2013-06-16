package com.citius.reservas.controllers.rest;

import com.citius.reservas.models.Ejemplo;
import com.citius.reservas.models.Reservation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Esther
 */
@Controller
@RequestMapping(value="/ejemplo")
//@Secured("IS_AUTHENTICATED_FULLY")
public class EjemploController {
    
    @ResponseBody 
    @RequestMapping(method = RequestMethod.GET)
    public Ejemplo read() {
        return new Ejemplo("asd", "<azsxc", 2);
    }
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Ejemplo create(@RequestBody Ejemplo ejmplo) {
        System.out.println(ejmplo);
        return ejmplo;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Reservation update() {
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete() {
        
    }
    
}
