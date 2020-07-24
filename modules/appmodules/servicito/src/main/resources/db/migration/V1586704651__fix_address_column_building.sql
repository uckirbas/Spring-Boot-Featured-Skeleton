-- New Migration
ALTER TABLE buildings
    ADD COLUMN `address_fixed` BIT(1) DEFAULT FALSE;