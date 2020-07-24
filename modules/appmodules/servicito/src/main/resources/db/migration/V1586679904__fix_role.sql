-- New Migration
UPDATE roles SET restricted=false WHERE role="ROLE_USER";

UPDATE roles SET name=role;
ALTER TABLE roles DROP COLUMN role;