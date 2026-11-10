-- Inserção de categorias padrão para o controle financeiro

INSERT INTO category (name, type)
SELECT 'Alimentação', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Alimentação');

INSERT INTO category (name, type)
SELECT 'Moradia', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Moradia');

INSERT INTO category (name, type)
SELECT 'Transporte', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Transporte');

INSERT INTO category (name, type)
SELECT 'Saúde', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Saúde');

INSERT INTO category (name, type)
SELECT 'Educação', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Educação');

INSERT INTO category (name, type)
SELECT 'Lazer', 'EXPENSE'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Lazer');

INSERT INTO category (name, type)
SELECT 'Salário', 'INCOME'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Salário');

INSERT INTO category (name, type)
SELECT 'Investimentos', 'INCOME'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Investimentos');

INSERT INTO category (name, type)
SELECT 'Freelance', 'INCOME'
    WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Freelance');