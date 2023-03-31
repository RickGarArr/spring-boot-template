package com.rga.gradesubmission.domain.utilities;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ToUpperCaseDeserializer extends StdDeserializer<String> {

    protected ToUpperCaseDeserializer() {
        super(String.class);
    }

    private static final long serialVersionUID = 7527542687158493910L;

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return _parseString(p, ctxt).toUpperCase();
    }

}