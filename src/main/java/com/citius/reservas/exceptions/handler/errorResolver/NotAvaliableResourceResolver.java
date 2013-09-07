/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.exceptions.NotAvaliableResources;
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
    private static final String MESSAGE = "error.notAvaliableResource";

    private NotAvaliableResourceResolver() {
        super();
    }

    private void setMessage(NotAvaliableResources notAvaliable, RestError error, HttpServletRequest request) {

        SimpleDateFormat format = new SimpleDateFormat("H:mm d/M/yyyy");
        String start = format.format(notAvaliable.getStart());
        String end = format.format(notAvaliable.getEnd());

        String builtMessage = this.i18n.getMessage(request, new Object[]{
            notAvaliable.getQuantityAvaliable().toString(),
            notAvaliable.getResourceName(),
            start,
            end
        }, MESSAGE);
        error.setMessage(builtMessage);
    }

    private void setDeveloperMessage(RestError error, HttpServletRequest request, Throwable exception) {
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
        for (NotAvaliableResources notAvaliable : ex.getNotAvaliableResources()) {

            RestError restError = new RestError(
                    error.getStatus(),
                    error.getCode(),
                    error.getMessage());

            this.setMessage(notAvaliable, restError, request);
            this.setDeveloperMessage(restError, request, exception);
            this.setTrace(restError, request, exception);

            restErrors.add(restError);
        }
        return restErrors;
    }
}
