/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
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
        setDateFormat(new ISO8601DateFormat());
    }
    
}
