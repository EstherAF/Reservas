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
public class OtherController {
    
    @RequestMapping("/")
    @Secured("IS_AUTHENTICATED_FULLY")
    public String redirectIndex() {
        System.out.println("index");
        return "secure/index";
    }
    
    @RequestMapping("/index")
    @Secured("IS_AUTHENTICATED_FULLY")
    public String index() {
        System.out.println("index");
        return "secure/index";
    }
    
    @RequestMapping("/admin")
    @Secured("ROLE_TIC")
    public String adminIndex() {
        System.out.println("index");
        return "admin/secured";
    }
}
