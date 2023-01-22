package cz.filmdb.serial;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Review;
import cz.filmdb.model.User;

import java.io.IOException;

public class ReviewSerializer extends StdSerializer<Review> {

    public ReviewSerializer() {
        this(null);
    }

    public ReviewSerializer(Class<Review> t) {
        super(t);
    }

    @Override
    public void serialize(Review review, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", review.getId());
        jsonGenerator.writeNumberField("score", review.getScore());
        jsonGenerator.writeStringField("comment", review.getComment());
        jsonGenerator.writeStringField("date", review.getDate().toString());

        {
            jsonGenerator.writeObjectFieldStart("user");

            User user = review.getUser();

            jsonGenerator.writeNumberField("id", user.getId());
            if (user.getUsername() != null) jsonGenerator.writeStringField("username", user.getUsername());

            jsonGenerator.writeEndObject();
        }

        {
            jsonGenerator.writeObjectFieldStart("filmwork");

            Filmwork filmwork = review.getFilmwork();

            jsonGenerator.writeNumberField("id", filmwork.getId());
            if (filmwork.getName() != null) jsonGenerator.writeStringField("name", filmwork.getName());

            jsonGenerator.writeEndObject();

        }

        jsonGenerator.writeEndObject();
    }
}
