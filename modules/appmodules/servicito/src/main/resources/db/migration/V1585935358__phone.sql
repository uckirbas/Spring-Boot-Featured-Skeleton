-- New Migration
alter table m_users
    change `phone_number` `phone` varchar(255) not null;
