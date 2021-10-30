package com.thedariusz.warnme.user.repository.entity;

import com.thedariusz.warnme.repository.entity.MeteoAlertOriginEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "warnme_param")
public class UserParametersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private OffsetDateTime sourceDateRefresh;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<MeteoAlertOriginEntity> meteoAlertOriginEntities = new HashSet<>();

    public UserParametersEntity() {
    }

    public UserParametersEntity(UserEntity user, OffsetDateTime sourceDateRefresh, Set<MeteoAlertOriginEntity> meteoAlertOriginEntities) {
        this.user = user;
        this.sourceDateRefresh = sourceDateRefresh;
        this.meteoAlertOriginEntities = meteoAlertOriginEntities;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OffsetDateTime getSourceDateRefresh() {
        return sourceDateRefresh;
    }

    public void setSourceDateRefresh(OffsetDateTime sourceDateRefresh) {
        this.sourceDateRefresh = sourceDateRefresh;
    }

    public Set<MeteoAlertOriginEntity> getMeteoAlertOriginEntities() {
        return meteoAlertOriginEntities;
    }

    public void setMeteoAlertOriginEntities(Set<MeteoAlertOriginEntity> meteoAlertOriginEntities) {
        this.meteoAlertOriginEntities = meteoAlertOriginEntities;
    }
}
