CREATE TABLE attribute_key
(
    id   UUID        NOT NULL,
    name VARCHAR(50) NOT NULL,

    CONSTRAINT pk_attribute_key PRIMARY KEY (id)
);

CREATE TABLE attribute_value
(
    id           UUID         NOT NULL,
    value        VARCHAR(100) NOT NULL,
    abbreviation VARCHAR(10),
    key_id       UUID         NOT NULL,

    CONSTRAINT pk_attribute_value PRIMARY KEY (id),
    CONSTRAINT fk_attribute_value_key FOREIGN KEY (key_id)
        REFERENCES attribute_key (id)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX uq_attribute_value_per_key
    ON attribute_value (key_id, LOWER(value));

CREATE UNIQUE INDEX uq_attribute_key_name_ci
    ON attribute_key (LOWER(name));