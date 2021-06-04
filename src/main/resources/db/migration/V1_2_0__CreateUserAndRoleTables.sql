CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY,
    enabled  INTEGER NOT NULL,
    password TEXT    NOT NULL,
    username TEXT    NOT NULL,

    CONSTRAINT uk_username UNIQUE (username)
);

CREATE TABLE user_role
(
    user_id BIGINT  NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_userrole_user FOREIGN KEY (user_id) references "user" (id),
    CONSTRAINT fk_userrole_role FOREIGN KEY (role_id) references role (id)
);

INSERT INTO role (name) VALUES ( 'ROLE_USER');
INSERT INTO role (name) VALUES ( 'ROLE_ADMIN');