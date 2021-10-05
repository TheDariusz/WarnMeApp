package com.thedariusz.warnme;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WarnMeApplicationIT extends IntegrationTestBase {

    @Test
    void shouldCreateWarnMeApplicationBean() {
        assertThat(context.containsBeanDefinition("warnMeApplication")).isTrue();
    }

}