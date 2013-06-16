/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.view;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Esther
 */
@Controller
@Secured("ROLE_TIC")
public class AdminController {   
    
    @RequestMapping("/admin")
    public String adminIndex() {
        System.out.println("index");
        return "admin/secured";
    }
}
