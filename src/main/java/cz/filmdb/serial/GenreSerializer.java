package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Genre;

import java.io.IOException;

public class GenreSerializer extends StdSerializer<Genre> {

    public GenreSerializer() {
        this(null);
    }

    public GenreSerializer(Class<Genre> t) {
        super(t);
    }

    @Override
    public void serialize(Genre genre, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id", genre.getId());
            jsonGenerator.writeStringField("name", genre.getName());

            // Filmworks
            jsonGenerator.writeArrayFieldStart("filmworks");

            for (Filmwork filmwork : genre.getFilmworks()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", filmwork.getFid());
                jsonGenerator.writeStringField("name", filmwork.getName());
                jsonGenerator.writeNumberField("audienceScore", filmwork.getAudienceScore());
                jsonGenerator.writeNumberField("criticsScore", filmwork.getCriticsScore());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }
}
