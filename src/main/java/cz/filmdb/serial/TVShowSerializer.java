package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.*;

import java.io.IOException;

public class TVShowSerializer extends StdSerializer<TVShow> {

    public TVShowSerializer() {
        this(null);
    }

    public TVShowSerializer(Class<TVShow> t) {
        super(t);
    }

    @Override
    public void serialize(TVShow tvShow, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", tvShow.getId());
        jsonGenerator.writeStringField("name", tvShow.getName());
        jsonGenerator.writeNumberField("audienceScore", tvShow.getAudienceScore());
        jsonGenerator.writeNumberField("criticsScore", tvShow.getCriticsScore());
        jsonGenerator.writeStringField("runningFrom",tvShow.getRunningFrom().toString());

        if (tvShow.getRunningTo() != null)
            jsonGenerator.writeStringField("runningTo",tvShow.getRunningTo().toString());


        // Genres
        {
            jsonGenerator.writeArrayFieldStart("genres");

            for (Genre genre : tvShow.getGenres()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", genre.getId());
                jsonGenerator.writeStringField("name", genre.getName());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }

        // Occupations
        {
            jsonGenerator.writeArrayFieldStart("occupations");

            for (Occupation occupation : tvShow.getOccupations()) {

                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", occupation.getId());
                jsonGenerator.writeStringField("role", occupation.getRole().name());

                Person person = occupation.getPerson();

                jsonGenerator.writeObjectFieldStart("person");
                jsonGenerator.writeNumberField("id",person.getId());
                jsonGenerator.writeStringField("firstName", person.getFirstName());
                jsonGenerator.writeStringField("lastName", person.getLastName());
                jsonGenerator.writeEndObject();

                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();

        }

        // Reviews
        {
            jsonGenerator.writeArrayFieldStart("reviews");

            for (Review review : tvShow.getReviews()) {

                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", review.getId());

                jsonGenerator.writeObjectFieldStart("user");
                jsonGenerator.writeNumberField("id", review.getUser().getId());
                jsonGenerator.writeStringField("username", review.getUser().getUsername());
                jsonGenerator.writeEndObject();

                jsonGenerator.writeStringField("comment", review.getComment());
                jsonGenerator.writeStringField("date", review.getDate().toString());
                jsonGenerator.writeNumberField("score", review.getScore());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();

        }

        jsonGenerator.writeEndObject();

    }
}
