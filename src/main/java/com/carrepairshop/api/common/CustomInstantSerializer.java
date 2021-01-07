package com.carrepairshop.api.common;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomInstantSerializer extends StdSerializer<Instant> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public CustomInstantSerializer() {
        this(null);
    }

    public CustomInstantSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Instant value,
                          JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        gen.writeString(formatter.format(value.atOffset(ZoneOffset.UTC)));
    }
}
