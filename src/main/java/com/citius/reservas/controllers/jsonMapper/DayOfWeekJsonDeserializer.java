/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.jsonMapper;

import com.citius.reservas.models.DayOfWeek;
import java.io.IOException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class DayOfWeekJsonDeserializer extends JsonDeserializer<DayOfWeek>{

    @Override
    public DayOfWeek deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Integer day = jp.getValueAsInt();
        return DayOfWeek.fromInteger(day);
    }
}
