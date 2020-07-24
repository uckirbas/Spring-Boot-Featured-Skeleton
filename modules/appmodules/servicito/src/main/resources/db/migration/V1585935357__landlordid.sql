-- New Migration
alter table buildings change `land_lord_id` `landlord_id` bigint(20) not null ;
