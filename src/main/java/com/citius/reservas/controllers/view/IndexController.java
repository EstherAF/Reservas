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
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@RequestMapping(headers={"content-type=text/html"})
public class IndexController {
    
    @RequestMapping("/")
    public String redirectIndex() {
        System.out.println("index");
        return "secure/index";
    }
    
    @RequestMapping("/index")
    public String index() {
        System.out.println("index");
        return "secure/index";
    }
}
