package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;

import java.io.IOException;

public class OccupationSerializer extends StdSerializer<Occupation> {

    public OccupationSerializer() {
        this(null);
    }

    public OccupationSerializer(Class<Occupation> t) {
        super(t);
    }

    @Override
    public void serialize(Occupation occupation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", occupation.getId());
        jsonGenerator.writeStringField("role", occupation.getRole().name());

        {
            jsonGenerator.writeObjectFieldStart("filmwork");

            Filmwork filmwork = occupation.getFilmwork();

            jsonGenerator.writeNumberField("id", filmwork.getFid());
            jsonGenerator.writeStringField("name", filmwork.getName());
            jsonGenerator.writeNumberField("audienceScore", filmwork.getAudienceScore());

            jsonGenerator.writeEndObject();
        }

        {

            jsonGenerator.writeObjectFieldStart("person");

            Person person = occupation.getPerson();

            jsonGenerator.writeNumberField("id", person.getId());
            jsonGenerator.writeStringField("firstName", person.getFirstName());
            jsonGenerator.writeStringField("lastName", person.getLastName());

            jsonGenerator.writeEndObject();

        }

        jsonGenerator.writeEndObject();
    }
}
