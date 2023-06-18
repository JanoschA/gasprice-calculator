CREATE TABLE "my_user"
(
    id             VARCHAR(255) NOT NULL,
    name           VARCHAR(255),
    email          VARCHAR(255),
    image_url      VARCHAR(255),
    email_verified BOOLEAN,
    password       VARCHAR(255),
    provider       INTEGER,
    provider_id    VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);