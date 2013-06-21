/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

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
@RequestMapping(value="/login", headers={"content-type=text/html"})
public class AccessController {

    @RequestMapping("/")
    public String login(Model model) {
        return "login";
    }
    
    @RequestMapping("/failure")
    public String loginFailure(Model model) {
        model.addAttribute("error", true);
        return "login";
    }
    
}
