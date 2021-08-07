package com.thedariusz.warnme;

import com.thedariusz.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WarnMeApplicationIT extends IntegrationTestBase {

    @Test
    void shouldCreateWarnMeApplicationBean() {
        assertThat(context.containsBeanDefinition("warnMeApplication")).isTrue();
    }

}