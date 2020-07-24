-- New Migration
ALTER TABLE apartments CHANGE `number_of_belcony` `number_of_balcony` TINYINT(4) NOT NULL;
ALTER TABLE apartments CHANGE `tiles` `tiled` BIT(1) NOT NULL;