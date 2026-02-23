CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE categories
(
    id        UUID                 NOT NULL,
    name      VARCHAR(150)         NOT NULL,
    version   BIGINT               NOT NULL,
    active    BOOLEAN DEFAULT TRUE NOT NULL,
    parent_id UUID,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE categories_aud
(
    rev       INTEGER NOT NULL,
    revtype   SMALLINT,
    id        UUID    NOT NULL,
    name      VARCHAR(150),
    active    BOOLEAN DEFAULT TRUE,
    parent_id UUID,
    CONSTRAINT pk_categories_aud PRIMARY KEY (rev, id)
);

CREATE TABLE revinfo
(
    rev      INTEGER NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

CREATE INDEX idx_category_name ON categories (name);
CREATE INDEX idx_category_parent_id ON categories (parent_id);

ALTER TABLE categories_aud
    ADD CONSTRAINT fk_categories_aud_on_rev FOREIGN KEY (rev) REFERENCES revinfo (rev);

ALTER TABLE categories
    ADD CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id) REFERENCES categories (id) ON DELETE RESTRICT;