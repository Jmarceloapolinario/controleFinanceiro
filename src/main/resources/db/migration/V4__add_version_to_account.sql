-- Adiciona a coluna version para controle de concorrência otimista (JPA @Version)
ALTER TABLE accounts
ADD COLUMN version BIGINT NOT NULL DEFAULT 0;