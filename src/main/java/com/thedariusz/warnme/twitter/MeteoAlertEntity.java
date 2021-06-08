package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.MeteoAlertOriginEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity(name = "meteo_alert")
public class MeteoAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "meteoalert_meteoalertcategory",
            joinColumns = @JoinColumn(name="alert_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<MeteoAlertCategoryEntity> categories;

    private String creationDate;

    @Column(length = 999)
    private String description;

    private String externalId;

    private String media;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "alert_origin_id", referencedColumnName = "id")
    private MeteoAlertOriginEntity meteoAlertOriginEntity;

    @Column(name = "created_date")
    private OffsetDateTime recordCreatedDate;

    public MeteoAlertEntity() {
    }

    public MeteoAlertEntity(int level, Set<MeteoAlertCategoryEntity> categories,
                            String creationDate, String description, String externalId, String media,
                            MeteoAlertOriginEntity meteoAlertOriginEntity, OffsetDateTime recordCreatedDate) {
        this.level = level;
        this.categories = categories;
        this.creationDate = creationDate;
        this.description = description;
        this.externalId = externalId;
        this.media = media;
        this.meteoAlertOriginEntity = meteoAlertOriginEntity;
        this.recordCreatedDate = recordCreatedDate;
    }

    public OffsetDateTime getRecordCreatedDate() {
        return recordCreatedDate;
    }

    public void setRecordCreatedDate(OffsetDateTime createdDate) {
        this.recordCreatedDate = createdDate;
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

    public Set<MeteoAlertCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<MeteoAlertCategoryEntity> categories) {
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

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof MeteoAlertEntity)) return false;

        MeteoAlertEntity that = (MeteoAlertEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id!=null ? id.hashCode():0;
    }
}
