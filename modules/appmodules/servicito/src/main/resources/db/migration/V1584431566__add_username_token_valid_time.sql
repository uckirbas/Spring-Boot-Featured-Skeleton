-- New Migration
ALTER TABLE ac_validation_tokens ADD COLUMN `username` VARCHAR(255) NOT NULL DEFAULT '';
ALTER TABLE ac_validation_tokens ADD COLUMN `token_valid_until` TIMESTAMP NULL;
