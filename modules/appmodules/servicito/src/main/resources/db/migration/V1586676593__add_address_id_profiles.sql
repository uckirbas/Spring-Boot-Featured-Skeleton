-- New Migration
ALTER TABLE profiles ADD COLUMN `address_id` BIGINT(20) NULL;
ALTER TABLE profiles ADD CONSTRAINT `FK_hfiweufh843r3rfuw8h` FOREIGN KEY (`address_id`) REFERENCES addr_addresses(`id`);