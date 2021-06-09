package com.thedariusz.warnme;

import java.util.List;
import java.util.Set;

public class MeteoAlert {

    private final int level;
    private final Set<String> categories;
    private final String creationDate;
    private final String description;
    private final String externalId;
    private final List<String> media;
    private final MeteoAlertOrigin meteoAlertOrigin;

    public MeteoAlert(int level, Set<String> categories, String creationDate, String description, String externalId, List<String> media, MeteoAlertOrigin meteoAlertOrigin) {
        this.level = level;
        this.categories = categories;
        this.creationDate = creationDate;
        this.description = description;
        this.externalId = externalId;
        this.media = media;
        this.meteoAlertOrigin = meteoAlertOrigin;
    }

    public List<String> getMedia() {
        return media;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public String getExternalId() {
        return externalId;
    }

    public MeteoAlertOrigin getMeteoAlertOrigin() {
        return meteoAlertOrigin;
    }

    @Override
    public String toString() {
        return "MeteoAlert{" +
                "level=" + level +
                ", categories=" + categories +
                ", creationDate='" + creationDate + '\'' +
                ", description='" + description + '\'' +
                ", externalId='" + externalId + '\'' +
                ", media=" + media +
                ", meteoAlertOrigin=" + meteoAlertOrigin +
                '}';
    }
}
