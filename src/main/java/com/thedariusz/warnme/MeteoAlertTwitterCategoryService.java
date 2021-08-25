package com.thedariusz.warnme;

import com.thedariusz.warnme.repository.MeteoAlertCategoryRepository;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryMapper;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeteoAlertTwitterCategoryService implements MeteoAlertCategoryService {

    private static final Set<String> METEO_ALERTS_CATEGORIES =
            Set.of("burze", "burza", "upał", "mróz", "przymrozki", "hydro", "deszcz", "wichura", "grad", "ulewa", "śnieg",
                    "burze z gradem", "intensywne opady deszczu", "intensywne opady śniegu", "mgła intensywnie osadzająca szadź",
                    "oblodzenie", "opady marznące", "opady śniegu", "roztopy", "silny deszcze z burzami",
                    "gęsta mgła", "silny mróz", "silny wiatr", "zawieje", "zamiecie śnieżne");

    private final MeteoAlertCategoryRepository categoryRepository;
    private final MeteoAlertCategoryMapper categoryMapper;

    public MeteoAlertTwitterCategoryService(MeteoAlertCategoryRepository categoryRepository, MeteoAlertCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Set<String> getCategoriesFromTags(List<String> hashTags) {
        return hashTags
                .stream()
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

    public Set<String> getAlertCategories(TweetDto tweetDto) {
        final Set<String> categoriesFromHashTags = getCategoriesFromTags(tweetDto.getHashtagsFromTweet());
        final Set<String> categoriesFromText = getCategoriesFromText(tweetDto.getText());

        return Stream.of(categoriesFromHashTags, categoriesFromText)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<MeteoAlertCategoryEntity> findCategories(Set<String> categories) {
        final List<MeteoAlertCategoryEntity> repositoryAll = categoryRepository.findAll();
        return categoryRepository.findByNameIn(categories);
    }

    public Set<MeteoAlertCategoryEntity> mapToEntities(Set<String> categories) {
        return categoryMapper.toEntity(categories);
    }

    public MeteoAlertCategoryEntity saveCategory(MeteoAlertCategoryEntity categoryEntity) {
        final MeteoAlertCategoryEntity categoryEntityFromDb = categoryRepository.findMeteoAlertCategoryEntityByName(categoryEntity.getName());
        if (categoryEntityFromDb==null) {
            return categoryRepository.save(categoryEntity);
        }
        return categoryEntityFromDb;

    }
}
