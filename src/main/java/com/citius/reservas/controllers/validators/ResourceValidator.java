/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.controllers.controllerModel.ResourceCustom;
import com.citius.reservas.models.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author Casa
 */
@Component
public class ResourceValidator extends GenericValidator<Resource> {
    
    public ResourceValidator(){
        super();
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Resource.class.equals(clazz) || ResourceCustom.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        Resource bean = (Resource) target;
        
        super.standardValidator.validate(target, errors);
        
        if(bean.getGroup()!=null && bean.getGroup().getId()<1)
            errors.reject("group", "error.form.resource.group.id.min");
        
//        if(target.getClass().equals(ResourceCustom.class)){
//            
//        }
    }
    
}
