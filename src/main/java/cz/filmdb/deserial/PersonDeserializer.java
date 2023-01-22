package cz.filmdb.deserial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import cz.filmdb.enums.RoleType;
import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PersonDeserializer extends StdDeserializer<Person> {

    public PersonDeserializer() {
        this(null);
    }

    public PersonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        // Optional
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;
        Set<Occupation> casting = getCasting(rootNode);

        //Required
        String firstName = rootNode.get("firstName").asText();
        String lastName = rootNode.get("lastName").asText();

        return new Person(id, firstName, lastName, casting);
    }


    private Set<Occupation> getCasting(JsonNode rootNode) {

        if (!rootNode.has("casting"))
            return Set.of();

        JsonNode occupationsSetNode = rootNode.get("casting");

        Set<Occupation> occupations = new HashSet<>();

        for (JsonNode genreNode : occupationsSetNode) {

            Long id = genreNode.get("id").asLong();
            RoleType role = RoleType.valueOf(genreNode.get("role").asText());

            JsonNode filmworkNode = genreNode.get("filmwork");

            Filmwork filmwork = new Filmwork();

            filmwork.setId(filmworkNode.get("id").asLong());
            filmwork.setName(filmworkNode.get("name").asText());
            filmwork.setAudienceScore(filmworkNode.has("audienceScore") ? (float) filmworkNode.get("audienceScore").asDouble() : 0.0f);
            filmwork.setCriticsScore(filmworkNode.has("criticsScore") ? (float) filmworkNode.get("criticsScore").asDouble() : 0.0f);

            occupations.add(new Occupation(id, filmwork, null, role));
        }

        return occupations;
    }
}
