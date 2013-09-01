/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.NotAvaliableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class ResolverFactory {
    @Autowired
    private NotAvaliableResourceResolver notAvaliableResourceResolver;
    @Autowired
    private InputRequestValidationResolver inputRequestValidationResolver;
    @Autowired
    private AccessDeniedResolver accessDeniedResolver;
    @Autowired
    private ThrowableResolver throwableResolver;

    public GenericErrorResolver getResolver(Throwable exception){
        if (exception.getClass().equals(NotAvaliableException.class)) {
            return this.notAvaliableResourceResolver;
        } else if (exception.getClass().equals(InputRequestValidationException.class)) {
            return this.inputRequestValidationResolver;
        }else if(exception.getClass().equals(AccessDeniedException.class)) {    
            return this.accessDeniedResolver;
        } else {
            return this.throwableResolver;
        }
    }
    
}
