CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE otp_config
(
    id          UUID PRIMARY KEY,
    code_length INTEGER NOT NULL,
    lifetime_seconds INTEGER NOT NULL
);

CREATE TABLE otp_codes
(
    id           UUID PRIMARY KEY,
    user_id      UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    operation_id UUID,
    code         VARCHAR(20) NOT NULL,
    status       VARCHAR(10) NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_status_expires_at ON otp_codes (status, expires_at);