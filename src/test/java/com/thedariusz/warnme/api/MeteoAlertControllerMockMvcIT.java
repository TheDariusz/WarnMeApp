package com.thedariusz.warnme.api;

import com.google.common.net.HttpHeaders;
import com.thedariusz.warnme.IntegrationTestBase;
import com.thedariusz.warnme.MeteoAlert;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.MeteoAlertOrigin;
import com.thedariusz.warnme.repository.MeteoAlertCategoryJpaRepository;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
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
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeteoAlertControllerMockMvcIT extends IntegrationTestBase {

    static ClientAndServer mockServer;

    @Autowired
    MeteoAlertDao meteoAlertDao;
    
    @Autowired
    MeteoAlertCategoryJpaRepository categoryJpaRepository;

    @BeforeEach
    public void init() {
        meteoAlertDao.deleteAll();
        mockServer.reset();
    }
    
    @BeforeAll
    public static void setup() {
        mockServer = ClientAndServer.startClientAndServer(8092);
    }

    @Test
    @WithMockUser("admin")
    public void syncTweetsShouldSaveMeteoAlertsAndCategories() throws Exception {
        //given
        meteoAlertDao.save(new MeteoAlert(
                1, Set.of("hydro"), "", "", "1427237056980324353", Collections.emptyList(), MeteoAlertOrigin.twitter("1","2")
        ));
        
        String userId = "1";
        mockTwitterResponse(userId);

        mockMvc.perform(post(ALERTS_PATH + "/1"))
                .andExpect(status().isOk());

        final List<MeteoAlert> meteoAlerts = meteoAlertDao.fetchAll();

        assertThat(meteoAlerts)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                        .withIgnoredFields("creationDate", "description", "media", "meteoAlertOrigin", "externalId")
                        .build())
                .contains(
                        meteoAlert(1, Set.of("hydro")),
                        meteoAlert(1, Set.of("burza", "deszcz", "grad"))
                );

    }

    private MeteoAlertCategoryEntity category(String name) {
        return new MeteoAlertCategoryEntity(name);
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