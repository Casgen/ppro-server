package cz.filmdb.deserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Review;
import cz.filmdb.model.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class ReviewDeserializer extends StdDeserializer<Review> {

    public ReviewDeserializer() {
        this(null);
    }

    public ReviewDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Review deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        String comment = rootNode.get("comment").asText();
        float score = (float) rootNode.get("score").asDouble();

        //Optional
        LocalDateTime creationDate = rootNode.has("date") ? LocalDateTime.parse(rootNode.get("date").asText()) : null;
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;
        User user = getUser(rootNode);
        Filmwork filmwork = getFilmwork(rootNode);

        return new Review(id, user, filmwork, creationDate, comment, score);
    }

    private Filmwork getFilmwork(JsonNode rootNode) {

        if (!rootNode.has("filmwork"))
            return null;

        JsonNode filmworkNode = rootNode.get("filmwork");

        Filmwork filmwork = new Filmwork();

        filmwork.setId(filmworkNode.get("id").asLong());
        filmwork.setName(filmworkNode.has("name") ? filmworkNode.get("name").asText() : null);

        return filmwork;

    }

    private User getUser(JsonNode rootNode) {
        
        if (!rootNode.has("user"))
            return null;
        
        JsonNode userNode = rootNode.get("user");
        
        User user = new User();
        
        user.setId(userNode.get("id").asLong());
        user.setUsername(userNode.has("username") ? userNode.get("username").asText() : null);
        
        return user;
    }
}
