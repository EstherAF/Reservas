/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Esther
 */
@Controller
public class AccessController {

    @RequestMapping("/index")
    public String helloWorld() {
        return "test";
    }
    
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/failure")
    public String loginFailure(Model model) {
        model.addAttribute("error", "Hubo un error");
        return "login";
    }
    
}
