
-- 1. Renomeia a coluna created_id para created_at na tabela accounts
ALTER TABLE accounts RENAME COLUMN created_id TO created_at;

-- 2. Altera a precisão da coluna amount na tabela transaction para NUMERIC(19, 2)
ALTER TABLE "transaction" ALTER COLUMN amount TYPE NUMERIC(19, 2);