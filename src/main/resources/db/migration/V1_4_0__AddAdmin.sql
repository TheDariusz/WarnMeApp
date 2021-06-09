INSERT INTO "user" (id, enabled, password, username)
VALUES (1001, 1, '$2a$10$lTiW.pKXMqhJUNYflj/IAu1NL5yHg9CX38Vh3wPxrcwJ2fwyv6xb2', 'user');

INSERT INTO user_role (user_id, role_id) VALUES (1001, 102);
