CREATE TABLE meteo_alert_category
(
    id BIGSERIAL primary key,
    name TEXT NOT NULL
);

CREATE TABLE meteoalert_meteoalertcategory
(
    alert_id    BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (alert_id, category_id),
    CONSTRAINT fk_meteoalertcategory_meteoalert FOREIGN KEY (alert_id) references "meteo_alert" (id),
    CONSTRAINT fk_meteoalertcategory_category FOREIGN KEY (category_id) references "meteo_alert_category" (id)
);
