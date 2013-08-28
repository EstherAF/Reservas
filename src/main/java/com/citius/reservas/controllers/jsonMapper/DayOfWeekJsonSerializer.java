/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.jsonMapper;

import com.citius.reservas.models.DayOfWeek;
import com.citius.reservas.models.Invitation;
import java.io.IOException;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class DayOfWeekJsonSerializer extends JsonSerializer<DayOfWeek>{

    @Override
    public void serialize(DayOfWeek value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeNumber(value.getNumber());
    }
    
}
