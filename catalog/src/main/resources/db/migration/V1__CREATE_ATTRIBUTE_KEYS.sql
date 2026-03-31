CREATE TABLE attribute_keys
(
    id   UUID        NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_attribute_keys PRIMARY KEY (id),
    CONSTRAINT uq_attribute_key_name UNIQUE (name)
);