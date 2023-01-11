package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Review;
import cz.filmdb.model.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

            jsonGenerator.writeNumberField("id", user.getId());
            jsonGenerator.writeStringField("email", user.getEmail());

            // Reviews
            jsonGenerator.writeArrayFieldStart("reviews");

                for (Review review : user.getUserReviews()) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeNumberField("id", review.getId());
                    jsonGenerator.writeStringField("comment", review.getComment());
                    jsonGenerator.writeNumberField("filmwork_id",review.getFilmwork().getFid());
                    jsonGenerator.writeStringField("date", review.getDate().toString());
                    jsonGenerator.writeNumberField("score", review.getScore());
                    jsonGenerator.writeEndObject();
                }

            jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
