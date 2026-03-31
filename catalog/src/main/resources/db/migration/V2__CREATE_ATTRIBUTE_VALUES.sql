CREATE TABLE attribute_values
(
    id           UUID         NOT NULL,
    key_id       UUID         NOT NULL,
    value        VARCHAR(100) NOT NULL,
    abbreviation VARCHAR(10),
    CONSTRAINT pk_attribute_values PRIMARY KEY (id)
);

ALTER TABLE attribute_values
    ADD CONSTRAINT fk_attribute_values_key
        FOREIGN KEY (key_id) REFERENCES attribute_keys (id)
            ON DELETE CASCADE;