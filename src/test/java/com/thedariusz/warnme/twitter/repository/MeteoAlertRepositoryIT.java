package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.IntegrationTestBase;
import com.thedariusz.warnme.MeteoAlert;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.MeteoAlertOrigin;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MeteoAlertRepositoryIT extends IntegrationTestBase {

    @Autowired
    MeteoAlertDao dao;

    @Ignore
    @Test
    void saveAndFetch() {
        final MeteoAlertOrigin origin = MeteoAlertOrigin.twitter("a", "1");
        MeteoAlert meteoAlert = new MeteoAlert(1, Set.of("a","b","c"), "", "", "", List.of("b"), origin);
        dao.save(meteoAlert);

        List<MeteoAlert> meteoAlerts = dao.fetchAll();
        MeteoAlert testAlert = meteoAlerts.get(0);
        assertThat(testAlert).isNotNull();
        assertThat(testAlert.getCategories())
                .hasSize(3)
                .containsExactly("a", "b", "c");


        assertThat(testAlert.getMeteoAlertOrigin())
                .usingRecursiveComparison()
                .isEqualTo(MeteoAlertOrigin.twitter("a", "1"));
    }

}