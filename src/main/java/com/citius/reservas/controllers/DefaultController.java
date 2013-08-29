/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 *
 * @author Esther Álvarez Feijoo
 */

@RequestMapping("/")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public interface DefaultController {
    
    @RequestMapping(value={"/","/index"}, 
        produces = MediaType.TEXT_HTML_VALUE, 
        method = RequestMethod.GET)
    public String index(Model model);
    
    @RequestMapping(value="/**", 
        produces = MediaType.TEXT_HTML_VALUE, 
        method = RequestMethod.GET)
    public String unmappedHTMLRequest(HttpServletRequest request) throws NoSuchRequestHandlingMethodException;
    
    @RequestMapping(value="/**", 
        produces = MediaType.APPLICATION_JSON_VALUE, 
        method = RequestMethod.GET)
    public String unmappedJSONRequest(HttpServletRequest request) throws NoSuchRequestHandlingMethodException;    
}
