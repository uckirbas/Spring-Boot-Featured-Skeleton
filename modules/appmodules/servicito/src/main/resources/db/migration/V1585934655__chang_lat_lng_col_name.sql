-- New Migration
ALTER TABLE addr_addresses
    CHANGE `lat` `latitude` DOUBLE NOT NULL;
ALTER TABLE addr_addresses
    CHANGE `lng` `longitude` DOUBLE NOT NULL;
