package com.thedariusz.warnme;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MeteoAlertGenericMapperTest {

    MeteoAlertCategoryService meteoAlertCategoryUtil;
    MeteoAlertGenericMapper meteoAlertGenericMapper = new MeteoAlertGenericMapper(meteoAlertCategoryUtil);

    @Test
    public void shouldReturnLevel1() {
        //given
        String textToSearchFor = "⚠Ostrzeżenia meteorologiczne! ▶Burze z gradem - st. 1;⛈ " +
                "(woj. zachodniopomorskie i lubuskie); Prognozowane są burze, którym miejscami będą towarzyszyć opady deszczu do 20 mm," +
                " lokalnie do 30 mm oraz porywy wiatru do 70 km/h. Miejscami grad. https://t.co/nmQ8eyQUJA";

        //when
        int alertLevel = meteoAlertGenericMapper.getAlertLevelFromTextField(textToSearchFor);

        //then
        assertThat(alertLevel)
                .isEqualTo(1);

    }

    @Test
    public void shouldReturnLevel2() {
        //given
        String textToSearchFor = "❗❗Uwaga Alert RCB❗❗ Intensywne opady deszczu w powiecie karkonoskim i " +
                "Jeleniej Górze w woj. dolnośląskim. Suma opadów może osiągnąć około 110 mm." +
                " IMGW wydał dla tych powiatów ostrzeżenia 3. stopnia. https://t.co/xG5MJb7pC2";

        //when
        int alertLevel = meteoAlertGenericMapper.getAlertLevelFromTextField(textToSearchFor);

        //then
        assertThat(alertLevel)
                .isEqualTo(3);

    }
}