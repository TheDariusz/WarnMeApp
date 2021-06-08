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

    @Test
    void testingCategies() {
        Set<String> category = Set.of("burze","deszcz");
        final MeteoAlertOrigin twitter = MeteoAlertOrigin.twitter("1", "1");
        final MeteoAlert meteoAlert1 = new MeteoAlert(1, category, "2021", "test1", "1", List.of("url1"), twitter);
        final MeteoAlert meteoAlert2 = new MeteoAlert(2, category, "2021", "test2", "2", List.of("url1"), twitter);
        dao.save(meteoAlert1);
        dao.save(meteoAlert2);
        List<MeteoAlert> meteoAlerts = dao.fetchAll();
        final List<MeteoAlertCategoryEntity> categoryAll = daoCategory.findAll();

        assertThat(categoryAll)
                .hasSize(2);


    }

}