-- New Migration
ALTER TABLE apartment_images CHANGE `image_paths`  `images` VARCHAR(255) NULL;
