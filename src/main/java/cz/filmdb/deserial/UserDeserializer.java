package cz.filmdb.deserial;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Review;
import cz.filmdb.model.User;
import cz.filmdb.model.UserRole;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;
        String email = rootNode.has("email") ? rootNode.get("email").asText() : null;
        UserRole role = rootNode.has("role") ? UserRole.valueOf(rootNode.get("role").asText()) : null;
        String password = rootNode.has("password") ? rootNode.get("password").asText() : null;
        String username = rootNode.get("username").asText();
        String profileImg = rootNode.has("profileImg") ? rootNode.get("profileImg").asText() : null;

        Set<Review> reviews = getReviews(rootNode);

        Set<Filmwork> plansToWatch = getWatch(rootNode, "plansToWatch");
        Set<Filmwork> isWatching = getWatch(rootNode, "isWatching");
        Set<Filmwork> wontWatch = getWatch(rootNode, "wontWatch");
        Set<Filmwork> hasWatched = getWatch(rootNode, "hasWatched");

        return new User(id,email,username,password, role, plansToWatch, isWatching, wontWatch, hasWatched, reviews, profileImg);
    }

    private Set<Review> getReviews(JsonNode rootNode) {
        if (!rootNode.has("reviews"))
            return Set.of();

        JsonNode reviewsSetNode = rootNode.get("reviews");
        
        Set<Review> reviews = new HashSet<>();

        for (JsonNode reviewNode : reviewsSetNode) {
            Long id = reviewNode.get("id").asLong();
            String comment = reviewNode.get("comment").asText();
            LocalDateTime date = LocalDateTime.parse(reviewNode.get("date").asText());
            float score = (float) reviewNode.get("score").asDouble();

            Filmwork filmwork;
            {
                JsonNode filmworkNode = reviewNode.get("filmwork");

                Long filmworkId = filmworkNode.get("id").asLong();
                String name = filmworkNode.get("name").asText();

                filmwork = new Filmwork(filmworkId, name);
            }

            reviews.add(new Review(id, null, filmwork, date, comment, score));
        }

        return reviews;

    }

    private Set<Filmwork> getWatch(JsonNode rootNode, String fieldName) {
        if (!rootNode.has(fieldName))
            return Set.of();

        JsonNode watchSetNode = rootNode.get(fieldName);

        Set<Filmwork> watchSet = new HashSet<>();

        for (JsonNode filmworkNode : watchSetNode) {

            Long id = filmworkNode.get("id").asLong();
            String name = filmworkNode.get("name").asText();

            watchSet.add(new Filmwork(id, name));
        }

        return watchSet;
    }
}
