package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.*;

import java.io.IOException;

public class MovieSerializer extends StdSerializer<Movie> {

    public MovieSerializer() {
        this(null);
    }

    public MovieSerializer(Class<Movie> t) {
        super(t);
    }

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id", movie.getId());
            jsonGenerator.writeStringField("name", movie.getName());
            jsonGenerator.writeNumberField("audienceScore", movie.getAudienceScore());
            jsonGenerator.writeNumberField("criticsScore", movie.getCriticsScore());
            jsonGenerator.writeStringField("releaseDate",movie.getReleaseDate().toString());

            // Genres
            jsonGenerator.writeArrayFieldStart("genres");

                for (Genre genre : movie.getGenres()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", genre.getId());
                    jsonGenerator.writeStringField("name", genre.getName());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

            jsonGenerator.writeArrayFieldStart("imgPaths");

            for (FilmworkImage image : movie.getImgPaths()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", image.getId());
                jsonGenerator.writeBooleanField("isTitle", image.isTitle());
                jsonGenerator.writeStringField("img", image.getImg());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();

            // Occupations
            jsonGenerator.writeArrayFieldStart("occupations");

                for (Occupation occupation : movie.getOccupations()) {

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

            // Occupations
            jsonGenerator.writeArrayFieldStart("reviews");

                for (Review review : movie.getReviews()) {
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

        jsonGenerator.writeEndObject();

    }
}
