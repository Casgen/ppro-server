package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;

import java.io.IOException;

public class PersonSerializer extends StdSerializer<Person> {

    public PersonSerializer() {
        this(null);
    }

    public PersonSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id",person.getId());
            jsonGenerator.writeStringField("firstName", person.getFirstName());
            jsonGenerator.writeStringField("lastName", person.getLastName());

            // Occupations
            jsonGenerator.writeArrayFieldStart("casting");

                for (Occupation occupation : person.getCasting()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", occupation.getId());

                    {
                        jsonGenerator.writeObjectFieldStart("filmwork");
                        jsonGenerator.writeNumberField("id", occupation.getFilmwork().getId());
                        jsonGenerator.writeStringField("name", occupation.getFilmwork().getName());
                        jsonGenerator.writeEndObject();
                    }

                    jsonGenerator.writeStringField("role", occupation.getRole().name());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }
}
