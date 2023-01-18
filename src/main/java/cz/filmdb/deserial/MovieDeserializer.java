package cz.filmdb.deserial;


import com.fasterxml.jackson.core.JacksonException;
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
import java.util.Iterator;
import java.util.Set;

public class MovieDeserializer extends StdDeserializer<Movie> {

    protected MovieDeserializer() {
        this(null);
    }

    protected MovieDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Long id = node.get("id").asLong();
        String name = node.get("name").asText();
        float audienceScore = (float) node.get("audienceScore").asDouble();
        float criticsScore = (float) node.get("criticsScore").asDouble();
        LocalDate releaseDate = LocalDate.parse(node.get("releaseDate").asText());

        Set<Genre> genres = getGenres(node.get("genres"));
        Set<Occupation> occupations = getOccupations(node.get("occupations"));
        Set<Review> reviews = getReviews(node.get("reviews"));

        return new Movie(id,name,audienceScore,criticsScore,genres,occupations,reviews,releaseDate);
    }

    private Set<Genre> getGenres(JsonNode jsonNode) {

        Set<Genre> genres = new HashSet<>();

        Long id = null;
        String name = null;

        for (JsonNode genreNode : jsonNode) {

            id = genreNode.get("id").asLong();
            name = genreNode.get("name").asText();

            genres.add(new Genre(id, name));

        }

        return genres;
    }

    private Set<Occupation> getOccupations(JsonNode jsonNode) {

        Set<Occupation> occupations = new HashSet<>();

        for (JsonNode genreNode : jsonNode) {
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

    private Set<Review> getReviews(JsonNode jsonNode) {

        Set<Review> reviews = new HashSet<>();

        for (JsonNode genreNode : jsonNode) {
            Long id = genreNode.get("id").asLong();
            String comment = genreNode.get("comment").asText();
            LocalDateTime date = LocalDateTime.parse(genreNode.get("date").asText());
            float score = (float) genreNode.get("score").asDouble();

            User user;
            {
                JsonNode userNode = genreNode.get("user");

                Long usersId = userNode.get("id").asLong();
                String username = userNode.get("username").asText();

                user = new User(id, username);
            }

            reviews.add(new Review(id, user, null, date, comment, score));
        }

        return reviews;
    }
}
