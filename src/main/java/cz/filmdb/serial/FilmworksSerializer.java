package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;

import java.io.IOException;
import java.util.Set;

public class FilmworksSerializer extends StdSerializer<Set<Filmwork>> {

    public FilmworksSerializer() {
        this(null);
    }

    public FilmworksSerializer(Class<Set<Filmwork>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Filmwork> filmworks, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();

        for (Filmwork filmwork : filmworks) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("filmwork_id", filmwork.getFid());
            jsonGenerator.writeStringField("name", filmwork.getName());
            jsonGenerator.writeNumberField("audience_score",filmwork.getAudienceScore());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();


    }
}
