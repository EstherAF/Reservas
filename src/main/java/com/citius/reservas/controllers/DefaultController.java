/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.citius.reservas.exceptions.UnknownResourceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Esther Álvarez Feijoo
 */

@Controller
public interface DefaultController {
    
    @RequestMapping(value={"/","/index"}, 
        produces = MediaType.TEXT_HTML_VALUE, 
        method = RequestMethod.GET)
    public String index(Model model);
    
//    @RequestMapping(value="/**", 
//        produces = MediaType.TEXT_HTML_VALUE, 
//        method = RequestMethod.GET)
//    public String unmappedHTMLRequest(HttpServletRequest request) throws UnknownResourceException;
//    
//    @RequestMapping(value="/**", 
//        produces = MediaType.APPLICATION_JSON_VALUE, 
//        method = RequestMethod.GET)
//    public String unmappedJSONRequest(HttpServletRequest request) throws UnknownResourceException;    
    
    @RequestMapping(value="/error",
        method = RequestMethod.GET)
    public String errorView(HttpServletRequest request) throws UnknownResourceException;
}
