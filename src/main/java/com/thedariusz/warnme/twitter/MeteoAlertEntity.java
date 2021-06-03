package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.MeteoAlertOriginEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "meteo_alert")
public class MeteoAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    private String categories;

    private String creationDate;

    private String description;

    private String externalId;

    private String media;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "alert_origin_id", referencedColumnName = "id")
    private MeteoAlertOriginEntity meteoAlertOriginEntity;

    public MeteoAlertEntity() {
    }

    public MeteoAlertEntity(int level, String categories, String creationDate, String description, String externalId, String media, MeteoAlertOriginEntity meteoAlertOriginEntity) {
        this.level = level;
        this.categories = categories;
        this.creationDate = creationDate;
        this.description = description;
        this.externalId = externalId;
        this.media = media;
        this.meteoAlertOriginEntity = meteoAlertOriginEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public MeteoAlertOriginEntity getMeteoAlertOriginEntity() {
        return meteoAlertOriginEntity;
    }

    public void setMeteoAlertOriginEntity(MeteoAlertOriginEntity meteoAlertOriginEntity) {
        this.meteoAlertOriginEntity = meteoAlertOriginEntity;
    }
}
