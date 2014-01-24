/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.impl;

import com.citius.reservas.business.AccessBusiness;
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

    @Override
    public void errorView(HttpServletRequest request) throws UnknownResourceException{
        throw new UnknownResourceException("There isn't resources for this request ("+request.getRequestURI()+")");
    }
    
}
