/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.impl;

import com.citius.reservas.controllers.DefaultController;
import com.citius.reservas.exceptions.UnknownResourceException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Controller
public class DefaultControllerImpl implements DefaultController{
    
//    @Autowired
//    private AccessBusiness accessBusiness;        

    @Override
    public String index(Model model) {
        return "redirect:/reservations/";
    }

//    @Override
//    public String unmappedHTMLRequest(HttpServletRequest request) throws UnknownResourceException {
//        throw new UnknownResourceException("There is no resource HTML for path for "+request.getRequestURI());
//    }
//
//    @Override
//    public String unmappedJSONRequest(HttpServletRequest request) throws UnknownResourceException {
//        throw new UnknownResourceException("There is no resource JSON for path for "+request.getRequestURI());
//    }

    @Override
    public String errorView(HttpServletRequest request) throws UnknownResourceException{
        throw new UnknownResourceException("There is no resource JSON for path for "+request.getRequestURI());
    }
    
}
