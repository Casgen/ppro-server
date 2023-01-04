package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Occupation;

import java.io.IOException;
import java.util.Set;

public class CastingSerializer extends StdSerializer<Set<Occupation>> {

    public CastingSerializer() {
        this(null);
    }

    public CastingSerializer(Class<Set<Occupation>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Occupation> occupations, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {


        // Set for the "occupations" field that it is a beginning of an array (starts with '[')
        jsonGenerator.writeStartArray();

        for (Occupation occupation : occupations) {
            jsonGenerator.writeStartObject(); // Indicate that an object is being written (starts with '{')
            jsonGenerator.writeNumberField("id", occupation.getId());                           // add a number field
            jsonGenerator.writeNumberField("filmwork_id", occupation.getFilmwork().getFid());   // add a number field
            jsonGenerator.writeStringField("role", occupation.getRole().name());                // add a string field
            jsonGenerator.writeEndObject(); //Indicate that an object creation is ending (ends with '}')
        }

        // Set for the "occupations" field that it is the ending of that array (starts with '[')
        jsonGenerator.writeEndArray();
    }
}
