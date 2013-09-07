/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.controllers.controllerModel.LoginStatus;
import com.citius.reservas.exceptions.handler.RestError;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class AccessDeniedResolver extends GenericErrorResolver{
    
    private static final String NOT_LOGGED_MESSAGE = "error.notLogged";
    private static final Integer NOT_LOGGED_CODE = 10200;

    public AccessDeniedResolver() {
        super();
    }
    
    private void setCode(RestError error, LoginStatus status) {
        if(!status.isLoggedIn())
            error.setCode(NOT_LOGGED_CODE);
    }
    
    private void setMessage(RestError error, HttpServletRequest request, LoginStatus status) {
        String messageReference = error.getMessage();
        
        if(!status.isLoggedIn())
            messageReference=NOT_LOGGED_MESSAGE;

        String message = this.i18n.getMessage(request, null, messageReference);
        error.setMessage(message);
    }

    private void setDeveloperMessage(RestError error, HttpServletRequest request) {
        String messageReference = error.getDeveloperMessage();

        String message = (messageReference.isEmpty()) ? 
                error.getMessage() :
                this.i18n.getMessage(request, null, messageReference);
        
        error.setDeveloperMessage(message);
    }

    @Override
    public Object populateError(RestError error, HttpServletRequest request, Throwable exception) {
        LoginStatus status = this.accessBusiness.getLoginStatus();
        
        this.setCode(error, status);
        this.setMessage(error, request, status);
        this.setDeveloperMessage(error, request);
        this.setTrace(error, request, exception);
        return error;
    }
    
}
