CREATE TABLE shared_income
(
    id                      UUID           NOT NULL,
    amount                  DECIMAL(10, 2) NOT NULL,
    user_id                 UUID           NOT NULL,
    income_id               UUID           NOT NULL,
    user_savings_account_id UUID           NOT NULL,
    CONSTRAINT pk_shared_income PRIMARY KEY (id)
);

CREATE TABLE user_savings_account
(
    id      UUID NOT NULL,
    name    VARCHAR(255),
    portion FLOAT,
    user_id UUID NOT NULL,
    CONSTRAINT pk_user_savings_account PRIMARY KEY (id)
);

ALTER TABLE shared_income
    ADD CONSTRAINT FK_SHARED_INCOME_ON_INCOME FOREIGN KEY (income_id) REFERENCES income (id);

ALTER TABLE shared_income
    ADD CONSTRAINT FK_SHARED_INCOME_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE shared_income
    ADD CONSTRAINT FK_SHARED_INCOME_ON_USER_SAVINGS_ACCOUNT FOREIGN KEY (user_savings_account_id) REFERENCES user_savings_account (id);

ALTER TABLE user_savings_account
    ADD CONSTRAINT FK_USER_SAVINGS_ACCOUNT_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE account_income
    DROP CONSTRAINT fk_account_income_on_user;

ALTER TABLE total_amount
    DROP CONSTRAINT fk_total_amount_on_user;

DROP TABLE account_income CASCADE;

DROP TABLE total_amount CASCADE;