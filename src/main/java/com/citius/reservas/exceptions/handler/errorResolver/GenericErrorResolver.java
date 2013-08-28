/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.customModel.LoginStatus;
import com.citius.reservas.controllers.i18n.i18nManager;
import com.citius.reservas.exceptions.handler.RestError;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public abstract class GenericErrorResolver {

    @Autowired
    protected i18nManager i18n;
    @Autowired
    protected AccessBusiness accessBusiness;

    public GenericErrorResolver() {
    }

    public i18nManager getI18n() {
        return i18n;
    }

    public void setI18n(i18nManager i18n) {
        this.i18n = i18n;
    }

    public void setTrace(RestError error, HttpServletRequest request, Throwable ex) {
        LoginStatus logged = accessBusiness.getLoginStatus();
        if (logged.isLoggedIn()
                && logged.isAdmin()) {
            error.setThrowable(ex);
        }
    }
    
    public abstract Object populateError(RestError error, HttpServletRequest request, Throwable exception);
}
