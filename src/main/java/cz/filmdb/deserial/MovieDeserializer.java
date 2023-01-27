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

public class MovieDeserializer extends StdDeserializer<Movie> {

    public MovieDeserializer() {
        this(null);
    }

    protected MovieDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        //Required
        String name = rootNode.get("name").asText();

        // Optional
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;
        LocalDate releaseDate = rootNode.has("releaseDate") ? LocalDate.parse(rootNode.get("releaseDate").asText()) : null;
        float audienceScore = (float) (rootNode.has("audienceScore") ? rootNode.get("audienceScore").asDouble() : 0.0);
        float criticsScore = (float) (rootNode.has("criticsScore") ? rootNode.get("criticsScore").asDouble() : 0.0);

        Set<Genre> genres = getGenres(rootNode);
        Set<Occupation> occupations = getOccupations(rootNode);
        Set<Review> reviews = getReviews(rootNode);
        Set<FilmworkImage> images = getImages(rootNode);

        return new Movie(id,name,audienceScore,criticsScore,genres,occupations,reviews,releaseDate, images);
    }

    private Set<FilmworkImage> getImages(JsonNode rootNode) {
        if (!rootNode.has("imgPaths"))
            return Set.of();

        JsonNode imgsSetNode = rootNode.get("imgPaths");

        Set<FilmworkImage> images = new HashSet<>();

        Long id;
        String path;
        boolean isTitle;

        for (JsonNode imgNode : imgsSetNode) {

            id = imgNode.get("id").asLong();
            path = imgNode.get("img").asText();
            isTitle = imgNode.has("isTitle") ? imgNode.get("isTitle").asBoolean() : false;

            images.add(new FilmworkImage(id, isTitle, path));

        }

        return images;
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

        for (JsonNode reviewNode : reviewsSetNode) {
            Long id = reviewNode.get("id").asLong();
            String comment = reviewNode.get("comment").asText();
            LocalDateTime date = LocalDateTime.parse(reviewNode.get("date").asText());
            float score = (float) reviewNode.get("score").asDouble();

            User user;
            {
                JsonNode userNode = reviewNode.get("user");

                Long usersId = userNode.get("id").asLong();
                String username = userNode.get("username").asText();

                user = new User(usersId, username);
            }

            reviews.add(new Review(id, user, null, date, comment, score));
        }

        return reviews;
    }

}
