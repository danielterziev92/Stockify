CREATE TYPE otp_type AS ENUM ('EMAIL_VERIFICATION');

CREATE TABLE otp
(
    id         UUID        NOT NULL,
    user_id    UUID        NOT NULL,
    type       otp_type    NOT NULL,
    code       VARCHAR(8)  NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT pk_otp PRIMARY KEY (id),
    CONSTRAINT uq_otp_user_type UNIQUE (user_id, type)
);