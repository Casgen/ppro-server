package cz.filmdb.deserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Genre;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GenreDeserializer extends StdDeserializer<Genre> {

    public GenreDeserializer() {
        this(null);
    }

    public GenreDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Genre deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        //Optional
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;

        //Required
        String name = rootNode.get("name").asText();

        return new Genre(id, name, getFilmworks(rootNode));
    }

    private Set<Filmwork> getFilmworks(JsonNode rootNode) {

        if (!rootNode.has("filmworks"))
            return Set.of();

        JsonNode filmworksNode = rootNode.get("filmworks");

        Set<Filmwork> filmworks = new HashSet<>();

        Filmwork filmwork;

        for (JsonNode item : filmworksNode) {

            filmwork = new Filmwork();

            filmwork.setId(item.get("id").asLong());
            filmwork.setName(item.get("name").asText());
            filmwork.setAudienceScore(item.has("audienceScore") ? (float) item.get("audienceScore").asDouble() : 0.0f);
            filmwork.setCriticsScore(item.has("criticsScore") ? (float) item.get("criticsScore").asDouble() : 0.0f);

            filmworks.add(filmwork);
        }

        return filmworks;
    }
}
