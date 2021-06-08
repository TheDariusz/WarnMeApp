CREATE TABLE meteo_alert_origin
(
    id               BIGSERIAL PRIMARY KEY,
    source_name      TEXT NOT NULL,
    source_author_id TEXT NOT NULL,
    original_id      TEXT
);

CREATE TABLE meteo_alert
(
    id              BIGSERIAL PRIMARY KEY,
    level           INTEGER NOT NULL,
    categories      TEXT    NOT NULL DEFAULT '',
    creation_date   TEXT    NOT NULL,
    description     TEXT    NOT NULL,
    external_id     TEXT    NOT NULL,
    media           TEXT,
    alert_origin_id BIGINT,
    created_date    TIMESTAMPTZ,

    CONSTRAINT fk_meteoalerts_alertsorigin FOREIGN KEY (alert_origin_id) references meteo_alert_origin (id)
);