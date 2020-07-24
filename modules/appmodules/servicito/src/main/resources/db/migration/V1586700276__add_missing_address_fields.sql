-- New Migration
ALTER TABLE `addr_addresses` ADD COLUMN `flat` VARCHAR(50) NULL;
ALTER TABLE `addr_addresses` ADD COLUMN `floor` TINYINT NOT NULL;
ALTER TABLE `addr_addresses` ADD COLUMN `house` VARCHAR(50) NULL;
ALTER TABLE `addr_addresses` ADD COLUMN `road` VARCHAR(50) NULL;
ALTER TABLE `addr_addresses` ADD COLUMN `post_code` VARCHAR(50) NULL;

ALTER TABLE `addr_addresses` MODIFY `area` VARCHAR(255) NOT NULL;
