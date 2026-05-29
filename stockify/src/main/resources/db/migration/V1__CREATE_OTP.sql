CREATE TABLE otp
(
    id         UUID PRIMARY KEY,
    user_id    UUID      NOT NULL,
    type       VARCHAR   NOT NULL,
    code       VARCHAR   NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_otp PRIMARY KEY (id)
);