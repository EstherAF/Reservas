/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.jsonMapper;

import com.citius.reservas.models.Invitation;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class InvitationJsonSerializer extends JsonSerializer<Invitation>{

    @Override
    public void serialize(Invitation value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeStringField("state", value.getState().toString());
            jgen.writeFieldName("guest");
            jgen.writeStartObject();
                jgen.writeStringField("uniqueName", value.getGuest().getUniqueName());
                jgen.writeStringField("fullName", value.getGuest().getFullName());
                jgen.writeStringField("email", value.getGuest().getEmail());
            jgen.writeEndObject();
            jgen.writeFieldName("reservation");
            jgen.writeStartObject();
                jgen.writeNumberField("id", value.getReservation().getId());
                jgen.writeStringField("name", value.getReservation().getName());
                jgen.writeStringField("owner", value.getReservation().getOwner().getFullName());
            jgen.writeEndObject();
        jgen.writeEndObject();
    }
    
}
