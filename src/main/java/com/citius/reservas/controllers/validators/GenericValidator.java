/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.controllers.i18n.i18nManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public abstract class GenericValidator<T> implements Validator{
    @Autowired
    protected  i18nManager i18;
    @Autowired
    protected LocalValidatorFactoryBean standardValidator;

    protected Class<T> type;
    
//    public void setValidator(LocalValidatorFactoryBean validatorFactory){
//        this.standardValidator=validatorFactory.getValidator();
//    }
    
    public GenericValidator() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return type.equals(clazz);
    }
    
}
