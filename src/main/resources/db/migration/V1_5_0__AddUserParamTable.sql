CREATE TABLE warnme_param
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id              BIGINT                                  NOT NULL,
    twitter_date_refresh TIMESTAMP with time zone,
    CONSTRAINT pk_warnme_param PRIMARY KEY (id)
);

ALTER TABLE warnme_param
    ADD CONSTRAINT FK_WARNME_PARAM_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);