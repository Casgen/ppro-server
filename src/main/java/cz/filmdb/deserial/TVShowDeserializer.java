package cz.filmdb.deserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.filmdb.enums.RoleType;
import cz.filmdb.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TVShowDeserializer extends StdDeserializer<TVShow> {

    public TVShowDeserializer() {
        this(null);
    }

    public TVShowDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public TVShow deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        //Required
        String name = rootNode.get("name").asText();
        LocalDate runningFrom = LocalDate.parse(rootNode.get("runningFrom").asText());

        // Optional
        int numberOfSeasons = rootNode.has("numberOfSeasons") ? rootNode.get("numberOfSeasons").asInt() : 0;
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;
        LocalDate runningTo = rootNode.has("runningTo") ? LocalDate.parse(rootNode.get("runningTo").asText()) : null;
        float audienceScore = (float) (rootNode.has("audienceScore") ? rootNode.get("audienceScore").asDouble() : 0.0);
        float criticsScore = (float) (rootNode.has("criticsScore") ? rootNode.get("criticsScore").asDouble() : 0.0);

        Set<Genre> genres = getGenres(rootNode);
        Set<Occupation> occupations = getOccupations(rootNode);
        Set<Review> reviews = getReviews(rootNode);

        return new TVShow(id, name, audienceScore, criticsScore, genres, occupations, reviews, numberOfSeasons, runningFrom, runningTo);
    }

    private Set<Genre> getGenres(JsonNode rootNode) {

        if (!rootNode.has("genres"))
            return Set.of();

        JsonNode genresSetNode = rootNode.get("genres");

        Set<Genre> genres = new HashSet<>();

        Long id;
        String name;

        for (JsonNode genreNode : genresSetNode) {

            id = genreNode.get("id").asLong();
            name = genreNode.get("name").asText();

            genres.add(new Genre(id, name));

        }

        return genres;
    }

    private Set<Occupation> getOccupations(JsonNode rootNode) {

        if (!rootNode.has("occupations"))
            return Set.of();

        JsonNode occupationsSetNode = rootNode.get("occupations");

        Set<Occupation> occupations = new HashSet<>();

        for (JsonNode genreNode : occupationsSetNode) {
            Long id = genreNode.get("id").asLong();
            String role = genreNode.get("role").asText();

            JsonNode personNode = genreNode.get("person");

            Long personsId = personNode.get("id").asLong();
            String firstName = personNode.get("firstName").asText();
            String lastName = personNode.get("lastName").asText();

            Person person = new Person(personsId, firstName, lastName);

            occupations.add(new Occupation(id, person, RoleType.valueOf(role)));
        }

        return occupations;
    }

    private Set<Review> getReviews(JsonNode rootNode) {

        if (!rootNode.has("reviews"))
            return Set.of();

        JsonNode reviewsSetNode = rootNode.get("reviews");

        Set<Review> reviews = new HashSet<>();

        for (JsonNode reviewsNode : reviewsSetNode) {
            
            Long id = reviewsNode.get("id").asLong();
            String comment = reviewsNode.get("comment").asText();
            LocalDateTime date = LocalDateTime.parse(reviewsNode.get("date").asText());
            float score = (float) reviewsNode.get("score").asDouble();

            User user;
            {
                JsonNode userNode = reviewsNode.get("user");

                Long usersId = userNode.get("id").asLong();
                String username = userNode.get("username").asText();

                user = new User(usersId, username);
            }

            reviews.add(new Review(id, user, null, date, comment, score));
        }

        return reviews;
    }
}
