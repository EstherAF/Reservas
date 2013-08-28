/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.exceptions.handler.errorResolver;

import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.handler.RestError;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class InputRequestValidationResolver extends GenericErrorResolver{

    public InputRequestValidationResolver(){
        super();
    }
    
    @Override
    public Object populateError(RestError predifinedError, HttpServletRequest request, Throwable exception) {
        List<RestError> restErrors = new ArrayList<>();
        
        if(! exception.getClass().equals(InputRequestValidationException.class))
            return null;
        
        for(ObjectError objectError:((InputRequestValidationException)exception).getObjectErrors()){
            RestError restError = new RestError(predifinedError.getStatus(), predifinedError.getCode());
            this.setMessage(restError, objectError, request);
            this.setDeveloperMessage(restError, request);
            this.setTrace(restError, request, exception);
            
            restErrors.add(restError);
        }
        
        return restErrors;
    }
    
    private void setMessage(RestError restError, ObjectError objectError, HttpServletRequest request){
        if(objectError.getClass().equals(FieldError.class)){
            FieldError fieldError = (FieldError) objectError;
            String message = this.i18n.getMessage(request, null, fieldError.getDefaultMessage());
            restError.setMessage(message);
        }
    }
    
    private void setDeveloperMessage(RestError error, HttpServletRequest request){
        error.setDeveloperMessage(error.getMessage());
    }
}
