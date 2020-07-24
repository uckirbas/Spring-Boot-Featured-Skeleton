-- New Migration
UPDATE apartment_images SET images=CONCAT('https://images.servicito.com', images) WHERE images != '';

UPDATE building_image_paths SET image_paths=CONCAT('https://images.servicito.com', image_paths) WHERE image_paths != '';