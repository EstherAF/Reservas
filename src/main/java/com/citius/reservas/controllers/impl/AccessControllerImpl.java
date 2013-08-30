/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.impl;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.*;
import com.citius.reservas.controllers.customModel.LoginStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;

/**
 *
 * @author Esther
 */
@Controller
public class AccessControllerImpl implements AccessController {

    @Autowired
    private AccessBusiness accessBusiness;

    @Override
    public LoginStatus getStatus() {
        return accessBusiness.getLoginStatus();
    }

    @Override
    public LoginStatus login(String username, String password) {
        return accessBusiness.login(username, password);
    }

    @Override
    public String loginView(HttpServletRequest request, Model model) {
        if (!this.getStatus().isLoggedIn()) {
            return "login";
        } else {
            return "redirect:/reservations/week";
        }
    }

    @Override
    public String loginFailure(Model model) {
        if (!this.getStatus().isLoggedIn()) {
            model.addAttribute("error", true);
            return "login";
        } else {
            return "redirect:/reservations/week";
        }
    }

//    @Override
//    public String index(Model model) {
//        return "redirect:/reservations/";
//    }

    @Override
    public String getTimeOut() throws HttpSessionRequiredException{
        throw new HttpSessionRequiredException("");
    }
}
