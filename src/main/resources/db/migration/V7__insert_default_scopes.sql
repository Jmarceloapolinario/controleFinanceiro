INSERT INTO scopes (name)
SELECT 'Admin'
    WHERE NOT EXISTS (SELECT 1 FROM scopes WHERE name = 'Admin');

INSERT INTO scopes (name)
SELECT 'Usuario'
    WHERE NOT EXISTS (SELECT 1 FROM scopes WHERE name = 'Usuario');