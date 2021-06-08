package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertCategory;
import com.thedariusz.warnme.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.MeteoAlertOrigin;
import com.thedariusz.warnme.MeteoAlertOriginEntity;
import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.MeteoAlertEntity;
import com.thedariusz.warnme.twitter.model.Meta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostgresMeteoAlertDaoIT {

    @Autowired
    MeteoAlertDao dao;

    @Autowired
    MeteoAlertCategorySpringDao daoCategory;

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