package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Genre;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Review;

import java.io.IOException;
import java.util.Set;

public class FilmworkSerializer extends StdSerializer<Filmwork> {

    public FilmworkSerializer() {
        this(null);
    }

    public FilmworkSerializer(Class<Filmwork> t) {
        super(t);
    }

    @Override
    public void serialize(Filmwork filmwork, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id", filmwork.getFid());
            jsonGenerator.writeStringField("name", filmwork.getName());
            jsonGenerator.writeNumberField("audienceScore", filmwork.getAudienceScore());
            jsonGenerator.writeNumberField("criticsScore", filmwork.getCriticsScore());

            // Genres
            jsonGenerator.writeArrayFieldStart("genres");

                for (Genre genre : filmwork.getGenres()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", genre.getId());
                    jsonGenerator.writeStringField("name", genre.getName());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

            // Occupations
            jsonGenerator.writeArrayFieldStart("occupations");

                for (Occupation occupation : filmwork.getOccupations()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", occupation.getId());
                    jsonGenerator.writeNumberField("person_id", occupation.getPerson().getId());
                    jsonGenerator.writeStringField("role", occupation.getRole().name());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

            // Occupations
            jsonGenerator.writeArrayFieldStart("reviews");

                for (Review review : filmwork.getReviews()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", review.getId());
                    jsonGenerator.writeNumberField("user_id", review.getUser().getId());
                    jsonGenerator.writeStringField("comment", review.getComment());
                    jsonGenerator.writeStringField("date", review.getDate().toString());
                    jsonGenerator.writeNumberField("score", review.getScore());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();

    }
}
