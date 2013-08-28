/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.citius.reservas.controllers.customModel.LoginStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Esther
 */
@RequestMapping("/login")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public interface AccessController {

    /*---------JSON---------*/
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public LoginStatus login(@RequestParam("username") String username, 
        @RequestParam("password") String password);

    @RequestMapping(value = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public LoginStatus getStatus();

    /*---------HTML---------*/
//    @RequestMapping(value={"/","/index",""}, 
//            produces = MediaType.TEXT_HTML_VALUE, 
//            method = RequestMethod.GET)
//    public String index(Model model);
    @RequestMapping(
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String loginView(HttpServletRequest request, Model model);

    @RequestMapping(value = "/failure",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String loginFailure(Model model);
}
