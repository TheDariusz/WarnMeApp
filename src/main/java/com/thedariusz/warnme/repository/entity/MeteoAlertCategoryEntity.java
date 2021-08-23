package com.thedariusz.warnme.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "meteo_alert_category")
public class MeteoAlertCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<MeteoAlertEntity> meteoAlertEntities = new HashSet<>();

    public MeteoAlertCategoryEntity() {
    }

    public MeteoAlertCategoryEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeteoAlertEntities(MeteoAlertEntity meteoAlertEntity) {
        this.meteoAlertEntities.add(meteoAlertEntity);
    }

    public Set<MeteoAlertEntity> getMeteoAlertEntities() {
        return meteoAlertEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;

        MeteoAlertCategoryEntity entity = (MeteoAlertCategoryEntity) o;

        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
