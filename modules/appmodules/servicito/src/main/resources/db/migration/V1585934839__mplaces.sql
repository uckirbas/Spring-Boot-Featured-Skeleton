-- New Migration
alter table buildings change `market_places` `marketplaces` VARCHAR(255) NULL;
