package com.thedariusz.warnme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MeteoAlertService {

    private static final Logger logger = LoggerFactory.getLogger(MeteoAlertService.class);

    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MeteoAlertDao meteoAlertDao;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao) {
        this.meteoAlertDao = meteoAlertDao;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        final List<String> newIds = meteoAlerts.stream().
                map(MeteoAlert::getExternalId)
                .collect(Collectors.toList());

        List<MeteoAlert> existingMeteoAlerts = meteoAlertDao.fetchExisting(newIds);

        final List<MeteoAlert> uniqueAlertsToSave = meteoAlerts.stream()
                .filter(meteoAlert -> externalIdEquals(existingMeteoAlerts, meteoAlert))
                .collect(Collectors.toList());

        logger.info("Saving {} new alerts", uniqueAlertsToSave.size());
        meteoAlertDao.saveAllWithCategories(uniqueAlertsToSave);
        
        
        /*
        * alert | category
        * 1         burze         
        * 2         deszcz
        * 3         burze
        * */

        /*  category_id | name
         *   10               burze
         *   20               deszcz
         * */
        
        /* fetched alert | category | action
        *  2                deszcz     nothing
        *  3                burze      nothing
        *  4                mroz       save alert and category
        *  5                deszcz     save alert with existing category
        * */
// -------------------------result-------------------------------------------------------------------------------
        /*
         * alert | category
         * 1         burze
         * 2         deszcz
         * 3         burze
         * 4         mroz
         * 5         deszcz
         * */

        /*  category_id | name
         *   10               burze
         *   20               deszcz
         *   30               mroz
         * */
        

//        alertToCategoriesMap.entrySet().stream()
//                .map(entry -> twitterCategoryService.fetchCategories(entry.getValue()))

//        uniqueAlertsToSave.forEach(meteoAlert -> {
//            final Set<String> categories = meteoAlert.getCategories();
//            Set<MeteoAlertCategoryEntity> categoriesForAlert = new HashSet<>();
//
//            categories.forEach(
//                    category -> twitterCategoryService.getCategoryEntityByName(category)
//                            .ifPresentOrElse(
//                                    categoriesForAlert::add,
//                                    () -> categoriesForAlert.add(twitterCategoryService.saveNewAlertCategory(category))
//                            ));
//
//            final MeteoAlertEntity meteoAlertEntity = meteoAlertMapper.toEntity(meteoAlert);
//            meteoAlertEntity.setCategories(categoriesForAlert);
//            meteoAlertJpaRepository.save(meteoAlertEntity);
//        });
//        meteoAlertDao.save();
    }

    public List<MeteoAlert> getMeteoAlertsFromDb() {
        return meteoAlertDao.fetchAll();
    }

    private boolean externalIdEquals(List<MeteoAlert> existingAlerts, MeteoAlert meteoAlert) {
        for (MeteoAlert alert : existingAlerts) {
            if (alert.getExternalId().equals(meteoAlert.getExternalId())) {
                return false;
            }
        }
        return true;
    }

    public String getRefreshDate() {
        return meteoAlertDao.getLatestCreatedAt()
                .map(DATE_TIME_FORMATTER::format)
                .orElse(EMPTY_STRING);
    }
}
