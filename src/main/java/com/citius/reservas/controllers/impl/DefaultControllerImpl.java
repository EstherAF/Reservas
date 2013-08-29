/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.impl;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.DefaultController;
import com.citius.reservas.controllers.customModel.LoginStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class DefaultControllerImpl implements DefaultController{
    
    @Autowired
    private AccessBusiness accessBusiness;        

    @Override
    public String index(Model model) {
        LoginStatus loginStatus = accessBusiness.getLoginStatus();
        if(loginStatus.isLoggedIn())
            return "/reservations";
        else
            return "/login";
    }

    @Override
    public String unmappedHTMLRequest(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
        throw new NoSuchRequestHandlingMethodException(request);
    }

    @Override
    public String unmappedJSONRequest(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
        throw new NoSuchRequestHandlingMethodException(request);
    }
    
}
