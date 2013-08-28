/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.business.impl;

import com.citius.reservas.controllers.DefaultController;
import com.citius.reservas.exceptions.UnknownResourceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Controller
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class DefaultControllerImpl implements DefaultController{

    @Override
    public String index(Model model) {
        return "redirect:/reservations/";
    }

    @Override
    public String unmappedHTMLRequest(HttpServletRequest request) 
            throws UnknownResourceException{
        throw new UnknownResourceException("There is no resource HTML for path for "+request.getRequestURI());
    }

    @Override
    public String unmappedJSONRequest(HttpServletRequest request) 
            throws UnknownResourceException{
        throw new UnknownResourceException("There is no resource JSON for path for "+request.getRequestURI());
    }
    
}
