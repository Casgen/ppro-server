package cz.filmdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.filmdb.controller.MovieController;
import cz.filmdb.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String apiUrl = "/".concat(MovieController.class.getAnnotation(RequestMapping.class).value()[0]);

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this,objectMapper);
    }

    String readJsonFile(String filePath) throws IOException {

        InputStream inputStream = new ClassPathResource(filePath).getInputStream();

        byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test
    public void getMovies() throws Exception {

        RequestBuilder req = MockMvcRequestBuilders.get(apiUrl)
                .param("page", "2")
                .param("size", "10")
                .param("sort","fid,desc");

        mvc.perform(req).andExpect(status().isOk());
    }

    @Test
    public void getMovie() throws Exception {

        RequestBuilder req = MockMvcRequestBuilders.get(apiUrl.concat("1"));

        mvc.perform(req).andExpect(status().isOk());
    }

    @Test
    public void getMoviesByGenre() throws Exception {

        RequestBuilder req = MockMvcRequestBuilders.get(apiUrl.concat("?genres=1"));

        mvc.perform(req).andExpect(status().isOk());
    }

}
