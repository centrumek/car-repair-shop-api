package com.carrepairshop.api.common;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomInstantDeserializer extends StdDeserializer<Instant> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public CustomInstantDeserializer() {
        this(null);
    }

    public CustomInstantDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Instant deserialize(JsonParser jsonparser,
                               DeserializationContext context) throws IOException {
        return formatter.parse(jsonparser.getText(), LocalDate::from).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
