-- New Migration
ALTER TABLE roles ADD COLUMN `restricted` BIT(1) DEFAULT TRUE;
