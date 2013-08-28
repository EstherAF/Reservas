/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions;

import java.util.List;
import org.springframework.validation.ObjectError;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class InputRequestValidationException extends Exception{

    private List<ObjectError> objectErrors;
    
    public InputRequestValidationException() {
        super();
    }

    public InputRequestValidationException(List<ObjectError> objectErrors) {
        super();
        this.objectErrors=objectErrors;
    }
    
    public InputRequestValidationException(String message) {
        super(message);
    }

    public List<ObjectError> getObjectErrors() {
        return objectErrors;
    }

    public void setObjectErrors(List<ObjectError> objectErrors) {
        this.objectErrors = objectErrors;
    }
    
}
