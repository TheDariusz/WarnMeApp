package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.model.Hashtag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertCategoryAssigment {

    private static final Set<String> METEO_ALERTS_CATEGORIES =
            Set.of("burze", "burza", "upał", "mróz", "przymrozki", "hydro", "deszcz", "wichura", "grad", "ulewa", "śnieg",
                    "burze z gradem", "intensywne opady deszczu", "intensywne opady śniegu", "mgła intensywnie osadzająca szadź",
                    "oblodzenie", "opady marznące", "opady śniegu", "roztopy", "silny deszcze z burzami",
                    "gęsta mgła", "silny mróz", "silny wiatr", "zawieje", "zamiecie śnieżne");


    public Set<String> getCategories(List<Hashtag> hashTags) {

        return hashTags
                .stream()
                .map(Hashtag::getTag)
                .map(String::toLowerCase)
                .filter(METEO_ALERTS_CATEGORIES::contains)
                .collect(Collectors.toSet());
    }

    public Set<String> getCategoriesFromText(String text) {
        text = text.toLowerCase();
        return METEO_ALERTS_CATEGORIES
                .stream()
                .filter(text::contains)
                .collect(Collectors.toSet());
    }

}
