-- New Migration
ALTER TABLE buildings ADD COLUMN `ad_type` VARCHAR(255) NULL;
ALTER TABLE buildings ADD COLUMN `has_lift` BIT(1) NOT NULL DEFAULT FALSE;
