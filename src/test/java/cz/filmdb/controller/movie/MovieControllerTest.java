package cz.filmdb.controller.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.filmdb.controller.MovieController;
import cz.filmdb.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    private static JacksonTester<Movie> json;

    private final String apiUrl = "/".concat(MovieController.class.getAnnotation(RequestMapping.class).value()[0]);

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this,objectMapper);
    }

    @Test
    public void getMovieTest() throws Exception {

        Movie expectedMovie = json.read("/controller/movie/getMovieTest.json").getObject();

        RequestBuilder req = MockMvcRequestBuilders.get(apiUrl.concat("1"));
        MvcResult result = mvc.perform(req).andReturn();

        Movie actualMovie = json.parse(result.getResponse().getContentAsString()).getObject();

        assertEquals(expectedMovie, actualMovie);
    }
}
