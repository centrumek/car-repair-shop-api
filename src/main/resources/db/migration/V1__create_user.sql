CREATE TABLE IF NOT EXISTS user
(
    uuid         UUID PRIMARY KEY,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    email        VARCHAR(255) UNIQUE      NOT NULL,
    password     VARCHAR(255)             NOT NULL,
    role         VARCHAR(255)             NOT NULL,
    first_name   VARCHAR(255)             NOT NULL,
    last_name    VARCHAR(255)             NOT NULL,
    mobile_phone VARCHAR(255)
);

CREATE index user_email_index ON user (email);