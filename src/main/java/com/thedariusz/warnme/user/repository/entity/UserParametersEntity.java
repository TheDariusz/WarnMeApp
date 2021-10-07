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

    private OffsetDateTime twitterDateRefresh;

    public UserParametersEntity() {
    }

    public UserParametersEntity(UserEntity user, OffsetDateTime twitterDateRefresh) {
        this.user = user;
        this.twitterDateRefresh = twitterDateRefresh;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OffsetDateTime getTwitterDateRefresh() {
        return twitterDateRefresh;
    }

    public void setTwitterDateRefresh(OffsetDateTime twitterDateRefresh) {
        this.twitterDateRefresh = twitterDateRefresh;
    }
}
