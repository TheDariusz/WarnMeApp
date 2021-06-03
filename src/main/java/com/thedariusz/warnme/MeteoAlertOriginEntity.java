package com.thedariusz.warnme;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "meteo_alert_origin")
public class MeteoAlertOriginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceName;

    private String sourceAuthorId;

    private String originalId;

    public MeteoAlertOriginEntity(String sourceName, String sourceAuthorId, String originalId) {
        this.sourceName = sourceName;
        this.sourceAuthorId = sourceAuthorId;
        this.originalId = originalId;
    }

    public MeteoAlertOriginEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getSourceAuthorId() {
        return sourceAuthorId;
    }

    public void setSourceAuthorId(String sourceAuthorId) {
        this.sourceAuthorId = sourceAuthorId;
    }

}
