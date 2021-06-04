package com.thedariusz.warnme.api;

import com.google.common.net.HttpHeaders;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.twitter.MeteoAlert;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.MediaType;
import org.mockserver.model.RequestDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MeteoAlertControllerMockMvcIT extends IntegrationTestBase {

    static ClientAndServer mockServer;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MeteoAlertDao meteoAlertDao;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() {
        meteoAlertDao.deleteAll();
        mockServer.reset();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @BeforeAll
    public static void setup() {
        mockServer = ClientAndServer.startClientAndServer(8092);

    }


    @Test
    @WithMockUser("admin")
    void fetchAllShouldSaveSingleAlertInMemory() throws Exception {
        //given
        String userId = "1";
        mockTwitterResponse(userId);

        mockMvc.perform(post(ALERTS_PATH + "/1"))
                .andDo(print())
                .andExpect(status().isOk());


        final List<MeteoAlert> meteoAlerts = meteoAlertDao.fetchAll();

        assertThat(meteoAlerts)
                .hasSize(4)
                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("creationDate", "description", "media", "meteoAlertOrigin", "externalId")
                        .build())
                .contains(
                        meteoAlert(0, Set.of("burze")),
                        meteoAlert(1, Set.of("burza", "deszcz", "śnieg", "burze")),
                        meteoAlert(2, Set.of("burza")),
                        meteoAlert(0, Set.of("upał"))
                );

    }

    private void mockTwitterResponse(String userId) {
        mockServer.when(fetchAllTweetsRequest(userId))
                .respond(this::allTweetsResponse);
    }

    private HttpResponse allTweetsResponse(HttpRequest request) throws IOException {
        String jsonResponse = Files.readString(Path.of("src/test/resources/twitter_response.json"));
        return HttpResponse.response()
                .withStatusCode(200)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(JsonBody.json(jsonResponse));
    }

    private RequestDefinition fetchAllTweetsRequest(String userId) {
        return HttpRequest.request()
                .withMethod("GET")
                .withPath("/2/users/" + userId + "/tweets")
                .withContentType(MediaType.APPLICATION_JSON)
                .withHeader(HttpHeaders.AUTHORIZATION, "Bearer abc");
    }

    private MeteoAlert meteoAlert(int level, Set<String> categories) {
        return new MeteoAlert(level, categories, null, null, "1139834822011084801", null, null);
    }

}