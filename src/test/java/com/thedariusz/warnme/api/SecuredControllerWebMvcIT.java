package com.thedariusz.warnme.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecuredControllerWebMvcIT {

    @Autowired
    MockMvc mockMvc;

    private static final String ALERTS_URI = "/alerts/twitter";

    UserRequestPostProcessor user = user("test");
    MockHttpServletRequestBuilder TWITTER_PAGE_REQUEST = get(ALERTS_URI)
            .with(user)
            .contentType(MediaType.TEXT_HTML);

    @Test
    void twitterPageRequestShouldAllowAuthenticatedUsers() throws Exception {
        mockMvc.perform(TWITTER_PAGE_REQUEST)
                .andExpect(status().isOk())
                .andExpect(view().name("twitter"));
    }

    MockHttpServletRequestBuilder TWITTER_PAGE_ANONYMOUS_REQUEST = get(ALERTS_URI)
            .contentType(MediaType.TEXT_HTML);

    @Test
    void anonymousRequestShouldRedirectToLoginPage() throws Exception {
        mockMvc.perform(TWITTER_PAGE_ANONYMOUS_REQUEST)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/alerts/login"));
    }
}
