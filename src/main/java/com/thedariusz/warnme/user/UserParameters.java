package com.thedariusz.warnme.user;

import java.time.OffsetDateTime;

public class UserParameters {
    private OffsetDateTime twitterDateRefresh;

    public UserParameters(OffsetDateTime twitterDateRefresh) {
        this.twitterDateRefresh = twitterDateRefresh;
    }

    public OffsetDateTime getTwitterDateRefresh() {
        return twitterDateRefresh;
    }

    public void setTwitterDateRefresh(OffsetDateTime twitterDateRefresh) {
        this.twitterDateRefresh = twitterDateRefresh;
    }
}
