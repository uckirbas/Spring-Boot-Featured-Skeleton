-- New Migration
-- auto-generated definition
create table privileges
(
    id            bigint auto_increment
        primary key,
    created       datetime     null,
    deleted       bit          not null,
    last_updated  datetime     null,
    uuid_str      varchar(255) null,
    label         varchar(255) not null,
    name          varchar(255) not null,
    created_by_id bigint       null,
    updated_by_id bigint       null,
    constraint unique_uuid
        unique (uuid_str),
    constraint FKewlbnm1g24vnjeuxlsk681k7j
        foreign key (created_by_id) references m_users (id),
    constraint FKj8t6kkmks6qe3dluky108cku
        foreign key (updated_by_id) references m_users (id)
);

-- auto-generated definition
create table privileges_access_urls
(
    privilege_id bigint       not null,
    access_urls  varchar(255) null,
    constraint FKp123f0u3yvp9ygtxbc1kmff5d
        foreign key (privilege_id) references privileges (id)
);

