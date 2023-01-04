package cz.filmdb.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import cz.filmdb.model.Review;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ReviewsSerializer extends StdSerializer<Set<Review>> {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public ReviewsSerializer() {
        this(null);
    }
    public ReviewsSerializer(Class<Set<Review>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Review> reviews, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();

        for (Review review : reviews) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("user_id", review.getUser().getId());
            jsonGenerator.writeNumberField("filmwork_id", review.getFilmwork().getFid());

            String date = review.getDate().format(dateFormat);

            jsonGenerator.writeStringField("date", date);
            jsonGenerator.writeStringField("comment", review.getComment());
            jsonGenerator.writeNumberField("score", review.getScore());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeEndArray();

    }

}
