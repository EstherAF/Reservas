/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.exceptions.NotAvaliable;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.handler.RestError;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class NotAvaliableResourceResolver extends GenericErrorResolver {

    public NotAvaliableResourceResolver() {
        super();
    }

    public void setMessage(NotAvaliable notAvaliable, RestError error, String message, HttpServletRequest request) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String start = format.format(notAvaliable.getStart());
        String end = format.format(notAvaliable.getEnd());

        String builtMessage = this.i18n.getMessage(request, new Object[]{
            notAvaliable.getQuantityAvaliable().toString(),
            notAvaliable.getResourceName(),
            start,
            end
        }, message);
        error.setMessage(builtMessage);
    }

    public void setDeveloperMessage(RestError error, HttpServletRequest request, Throwable exception) {
        error.setDeveloperMessage(error.getMessage());
    }

    @Override
    public Object populateError(RestError error, HttpServletRequest request, Throwable exception) {
        if (!exception.getClass().equals(NotAvaliableException.class)) {
            return null;
        }

        NotAvaliableException ex = (NotAvaliableException) exception;
        List<RestError> restErrors = new ArrayList<>();

        //create RestError for each not avaliable resource
        String message = this.i18n.getMessage(request, null, error.getMessage());
        for (NotAvaliable notAvaliable : ex.getNotAvaliableResources()) {

            RestError restError = new RestError(
                    error.getStatus(),
                    error.getCode(),
                    error.getMessage());

            this.setMessage(notAvaliable, restError, message, request);
            this.setDeveloperMessage(restError, request, exception);
            this.setTrace(restError, request, exception);

            restErrors.add(restError);
        }
        return restErrors;
    }
}
