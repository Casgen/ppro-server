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

public class OccupationDeserializer extends StdDeserializer<Occupation> {

    public OccupationDeserializer() {
        this(null);
    }

    public OccupationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Occupation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        //Optional
        Long id = rootNode.has("id") ? rootNode.get("id").asLong() : null;

        //Required
        RoleType roleType = RoleType.valueOf(rootNode.get("role").asText());
        Filmwork filmwork = getFilmWork(rootNode);
        Person person = getPerson(rootNode);

        return new Occupation(id, filmwork, person, roleType);
    }

    private Person getPerson(JsonNode rootNode) {

        if (!rootNode.has("person"))
            return null;

        JsonNode personNode = rootNode.get("person");

        return new Person(
                personNode.get("id").asLong(),
                personNode.has("firstName") ? personNode.get("firstName").asText() : null,
                personNode.has("lastName") ? personNode.get("lastName").asText() : null
        );
    }

    private Filmwork getFilmWork(JsonNode rootNode) {

        if (!rootNode.has("filmwork"))
            return null;

        JsonNode filmworkNode = rootNode.get("filmwork");

        return new Filmwork(
                filmworkNode.get("id").asLong(),
                filmworkNode.has("name") ? filmworkNode.get("name").asText() : null
        );

    }
}
