/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.models.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Casa
 */
@Component
public class ResourceValidator extends GenericValidator<Resource> {

    @Override
    public void validate(Object target, Errors errors) {
        Resource bean = (Resource) target;
        
        this.standardValidator.validate(target, errors);
        
        if(bean.getGroup()!=null && bean.getGroup().getId()<1)
            errors.reject("group", "error.form.resource.group.id.min");
    }
    
}
