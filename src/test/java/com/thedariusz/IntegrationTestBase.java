package com.thedariusz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestBase {

    static {
        System.setProperty("catalina.home", "dist");
    }

    protected static final String ALERTS_PATH = "/alerts";

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

}

