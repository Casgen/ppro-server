package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OccupationsSerializer extends StdSerializer<Set<Occupation>> {

    public OccupationsSerializer(Class<Set<Occupation>> t) {
        super(t);
    }

    public OccupationsSerializer() { this(null); }

    @Override
    public void serialize(Set<Occupation> occupations, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        //Here we are starting with a completely blank value (no brackets like {} or, [] under the 'occupations' field)

        // Set for the "occupations" field that it is a beginning of an array (starts with '[')
        jsonGenerator.writeStartArray();

        for (Occupation occupation : occupations) {
            jsonGenerator.writeStartObject(); // Indicate that an object is being written (starts with '{')
            jsonGenerator.writeNumberField("id", occupation.getId());                       // add a number field
            jsonGenerator.writeNumberField("person_id", occupation.getPerson().getId());    // add a number field
            jsonGenerator.writeStringField("role", occupation.getRole().name());            // add a string field
            jsonGenerator.writeEndObject(); //Indicate that an object creation is ending (ends with '}')
        }

        // Set for the "occupations" field that it is the ending of that array (starts with '[')
        jsonGenerator.writeEndArray();

    }
}
