/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.controllers.helper.i18nControllerHelper;
import com.citius.reservas.models.Resource;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Casa
 */
public class ResourcesValidator implements Validator {
    @Autowired
    private  i18nControllerHelper i18;

    
            
    @Override
    public boolean supports(Class Resource) {
        return Resource.class.equals(Resource);
    }

    @Override
    public void validate(Object o, Errors e) {
        Resource r = (Resource) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "error.form.reservation.name.empty");
        if(r.getId()<= 0){
                e.rejectValue("id", "error.form.reservation.id");
        }else if(r.getName().length() > 50 ){
                e.rejectValue("name", "error.form.reservation.name.maxlenght");
        }else if (r.getDescription().length() > 250){
                e.rejectValue("description", "error.form.reservation.description.maxlenght");
        }                
    }
    
}
