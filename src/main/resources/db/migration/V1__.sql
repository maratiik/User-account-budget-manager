CREATE TABLE account_income
(
    id         UUID           NOT NULL,
    name       VARCHAR(255)   NOT NULL,
    date       date           NOT NULL,
    amount     DECIMAL(10, 2) NOT NULL,
    proportion FLOAT          NOT NULL,
    user_id    UUID           NOT NULL,
    CONSTRAINT pk_account_income PRIMARY KEY (id)
);

CREATE TABLE income
(
    id      UUID           NOT NULL,
    date    date           NOT NULL,
    amount  DECIMAL(10, 2) NOT NULL,
    user_id UUID           NOT NULL,
    CONSTRAINT pk_income PRIMARY KEY (id)
);

CREATE TABLE total_amount
(
    id         UUID           NOT NULL,
    name       VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    amount     DECIMAL(10, 2) NOT NULL,
    user_id    UUID           NOT NULL,
    CONSTRAINT pk_total_amount PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id          UUID         NOT NULL,
    fingerprint VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_fingerprint UNIQUE (fingerprint);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE account_income
    ADD CONSTRAINT FK_ACCOUNT_INCOME_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE income
    ADD CONSTRAINT FK_INCOME_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE total_amount
    ADD CONSTRAINT FK_TOTAL_AMOUNT_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);