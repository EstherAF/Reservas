/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.view;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Esther
 */
@Controller
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class AccessController {

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @RequestMapping("/login/failure")
    public String loginFailure(Model model) {
        model.addAttribute("error", true);
        return "login";
    }
    
}
