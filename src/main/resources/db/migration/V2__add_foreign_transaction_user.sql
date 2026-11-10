ALTER TABLE "transaction"
ADD COLUMN IF NOT EXISTS user_id BIGINT;

ALTER TABLE "transaction"
ADD CONSTRAINT fk_transactions_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE;