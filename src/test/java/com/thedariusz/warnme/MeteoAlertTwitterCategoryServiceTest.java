package com.thedariusz.warnme;

import com.thedariusz.IntegrationTestBase;
import com.thedariusz.warnme.repository.MeteoAlertCategoryRepository;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MeteoAlertTwitterCategoryServiceTest extends IntegrationTestBase {

    @Autowired
    MeteoAlertCategoryService meteoAlertTwitterCategoryService;

    @Ignore
    @Test
    void shouldReturnEmptySetOfCategoryEntities() {
        //given
        Set<String> categories = Set.of("burze", "upał");

        //when
        final Set<MeteoAlertCategoryEntity> meteoAlertCategoryEntities = meteoAlertTwitterCategoryService.findCategories(categories);

        //then
        assertThat(meteoAlertCategoryEntities)
                .hasSize(2);


    }
}