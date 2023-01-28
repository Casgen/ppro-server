package cz.filmdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.filmdb.model.AuthenticationResponse;
import cz.filmdb.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    private final String apiUrl = "/".concat(AuthenticationController.class.getAnnotation(RequestMapping.class).value()[0]);

    private static JacksonTester<AuthenticationResponse> jsonAuthRes;
    private static JacksonTester<RegisterRequest> jsonRegisterReq;

    @BeforeEach
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    void register() throws Exception {

        RegisterRequest newUser = new RegisterRequest("username", "user@gmail.com", "123456789");
        String newUserJson = jsonRegisterReq.write(newUser).getJson();

        RequestBuilder req = MockMvcRequestBuilders.post(apiUrl.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(newUserJson);

        MvcResult result = mvc.perform(req).andExpect(status().isOk()).andReturn();

        AuthenticationResponse authRes = jsonAuthRes.parseObject(result.getResponse().getContentAsString());

        assertNotNull(authRes.getToken());
    }
}
