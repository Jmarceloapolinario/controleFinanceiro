-- 1. Criação da tabela de usuários
CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Criação da tabela de contas (relacionada com users)
CREATE TABLE accounts (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  balance NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
  user_id BIGINT NOT NULL,
  created_id TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Criação da tabela de categorias
CREATE TABLE category (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL
);

-- 4. Criação da tabela de transações (relacionada com accounts e category)
CREATE TABLE "transaction" (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    transaction_date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    account_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT fk_transactions_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT
);