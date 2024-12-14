ALTER TABLE user_savings_account
    DROP COLUMN portion;

ALTER TABLE user_savings_account
    ADD portion DECIMAL(3, 2);