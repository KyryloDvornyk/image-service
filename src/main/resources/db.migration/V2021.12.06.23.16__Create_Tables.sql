CREATE TABLE accounts
(
    id       BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    login    VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
);

CREATE TABLE images
(
    id           BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR NOT NULL,
    content_type VARCHAR NOT NULL,
    size         BIGINT  NOT NULL,
    account_id   BIGINT  NOT NULL REFERENCES accounts (id)
);

CREATE TABLE tags
(
    id   BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR NOT NULL
);

CREATE TABLE images_tags
(
    image_id BIGINT NOT NULL REFERENCES images (id),
    tag_id   BIGINT NOT NULL REFERENCES tags (id),
    PRIMARY KEY (image_id, tag_id)
);
