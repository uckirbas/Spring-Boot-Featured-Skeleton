-- New Migration
ALTER TABLE `profiles` CHANGE `profile_picture_path` `photo` VARCHAR(511) NULL;
ALTER TABLE `profiles` CHANGE `birth_date` `birthday` DATETIME NULL;