/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.citius.reservas.controllers.controllerModel.LoginStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Esther
 */
@RequestMapping("/login")
public interface AccessController {
    
    /*
     * Content: JSON
     * Description: Función de validación por AJAX
     * @param userName nombre de usuario
     * @param password contraseña
     * @return Estado de validación en el sistema
     * @see LoginStatus
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public LoginStatus login(@RequestParam("username") String username, 
        @RequestParam("password") String password);

    
    /*
     * Content: JSON
     * Description: Obtener estado de validación
     * @return Estado de validación en el sistema
     * @see LoginStatus
     */
    @RequestMapping(value = "/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public LoginStatus getStatus();
    
    
    @RequestMapping(value = {"/",""},
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public String getTimeOut() throws HttpSessionRequiredException;

    /*
     * Content: HTML
     * Description: Obtener vista para la validación del usuario
     */
    @RequestMapping(
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String loginView(HttpServletRequest request, Model model);

    /*
     * Content: HTML
     * Description: Obtener vista de error de validación del usuario
     */
    @RequestMapping(value = "/failure",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String loginFailure(Model model);
}
