-- 1) DROP TABLES

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';

        -- 2) Levantar la Spring Boot App (Para crear las Entities y Tables)

-- 3) INSERT Roles

INSERT INTO roles (id, created_by, created_date, deleted, modified_by, modified_date, description, name)
values
        (1, 'System', NOW(), false, null, null, 'Admin Role', 'ROLE_ADMIN'),
        (2, 'System', NOW(), false, null, null, 'User Role', 'ROLE_USER');






