-- New Migration
alter table buildings add column `address_id` bigint null;
alter table buildings add constraint `fk_address_id` foreign key (address_id) references addr_addresses(id);