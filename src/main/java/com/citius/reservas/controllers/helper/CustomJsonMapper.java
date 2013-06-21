/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class CustomJsonMapper extends ObjectMapper{
    
    public CustomJsonMapper(){
        super();
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
    }
    
}
