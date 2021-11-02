package com.thedariusz.warnme.user.repository.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "warnme_param")
public class UserParametersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private OffsetDateTime sourceDateRefresh;

    private String sourceId;

    public UserParametersEntity() {
    }

    public UserParametersEntity(UserEntity user, OffsetDateTime sourceDateRefresh, String sourceId) {
        this.user = user;
        this.sourceDateRefresh = sourceDateRefresh;
        this.sourceId = sourceId;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
