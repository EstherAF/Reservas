/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.citius.reservas.exceptions.UnknownResourceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Controller
public interface DefaultController {
    
    /*
     * Content: HTML
     * Description: Mostrar la vista por defecto de la aplicación
     */
    @RequestMapping(value={"/","/index"}, 
        produces = MediaType.TEXT_HTML_VALUE, 
        method = RequestMethod.GET)
    public String index(Model model);
    
    
    /*
     * Content: HTML & JSON
     * Description: Lanza una excapción, que se capturará con ApplicationHandlerException
     */
    @RequestMapping(value="/error",
        method = RequestMethod.GET)
    public void errorView(HttpServletRequest request) throws UnknownResourceException;
}
