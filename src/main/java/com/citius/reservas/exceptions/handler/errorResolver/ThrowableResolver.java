/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.exceptions.handler.RestError;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class ThrowableResolver extends GenericErrorResolver{

    private static final String DEFAULT_MESSAGE_CODE = "error.default";
    private static final String DEFAULT_DEVELOPER_MESSAGE_CODE = "error.dev.default";

    public ThrowableResolver() {
        super();
    }
    
    private void setMessage(RestError error, HttpServletRequest request, Throwable exception) {
        String messageReference = error.getMessage();

        if (messageReference.isEmpty()) {
            messageReference = DEFAULT_MESSAGE_CODE;
        }

        String message = this.i18n.getMessage(request, null, messageReference);
        error.setMessage(message);
    }

    private void setDeveloperMessage(RestError error, HttpServletRequest request, Throwable exception) {
        String messageReference = error.getDeveloperMessage();

        if (messageReference.isEmpty()) {
            messageReference = DEFAULT_DEVELOPER_MESSAGE_CODE;
        }

        String message = this.i18n.getMessage(request, null, messageReference);
        error.setDeveloperMessage(message);
    }

    @Override
    public Object populateError(RestError error, HttpServletRequest request, Throwable exception) {
        this.setMessage(error, request, exception);
        this.setDeveloperMessage(error, request, exception);
        this.setTrace(error, request, exception);
        return error;
    }
    
}
