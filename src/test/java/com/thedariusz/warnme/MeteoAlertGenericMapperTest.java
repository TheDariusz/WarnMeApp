package com.thedariusz.warnme;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MeteoAlertGenericMapperTest {

    MeteoAlertCategoryService meteoAlertCategoryUtil;
    MeteoAlertGenericMapper meteoAlertGenericMapper = new MeteoAlertGenericMapper(meteoAlertCategoryUtil);

    @Test
    public void shouldReturnLevel1() {
        //given
        String textToSearchFor = "W związku z opadami dla zlewni rzek południowo-wschodniej Polski wydano ostrzeżenia hydrologiczne 1⁰." +
                " Mogą zostać przekroczone stany ostrzegawcze a wzrosty mogą miejscami być gwałtowne." +
                " Wzrostów spodziewamy się również na Podlasiu i Wybrzeżu RP. \uD83C\uDF0A #IMGW #hydro #rzeki #uwaga https://t.co/VPgqC20T62";

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