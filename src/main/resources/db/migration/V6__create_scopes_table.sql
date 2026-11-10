-- 1. Criação da tabela de scopes
CREATE TABLE scopes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. Criação da tabela associativa (Many-to-Many entre users e scopes)
CREATE TABLE users_scopes (
      user_id BIGINT NOT NULL,
      scope_id BIGINT NOT NULL,
      PRIMARY KEY (user_id, scope_id),
      CONSTRAINT fk_users_scopes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
      CONSTRAINT fk_users_scopes_scope FOREIGN KEY (scope_id) REFERENCES scopes(id) ON DELETE CASCADE
);