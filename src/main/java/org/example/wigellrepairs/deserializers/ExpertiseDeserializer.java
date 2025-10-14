package org.example.wigellrepairs.deserializers;

import java.io.IOException;

import org.example.wigellrepairs.enums.Expertise;
import org.example.wigellrepairs.exceptions.ResourceWithValueNotFoundException;
import org.example.wigellrepairs.utils.Checker;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ExpertiseDeserializer extends JsonDeserializer<Expertise> {

	@Override
	public Expertise deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String value = p.getText();
        if(Checker.validEnum(value))
            return Expertise.valueOf(value.toUpperCase());
        throw new ResourceWithValueNotFoundException("Expertise", value);
	}
}
