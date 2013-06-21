/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.models.Resource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Casa
 */
public class ResourcesValidator implements Validator {

    
    @Override
    public boolean supports(Class Resource) {
        return Resource.class.equals(Resource);
    }

    @Override
    public void validate(Object o, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "name", "error.login.lenght");
        Resource r = (Resource) o;
        if(r.getName().length() > tralali ){
                e.reject(); e.
                
        }
    }
    
}
