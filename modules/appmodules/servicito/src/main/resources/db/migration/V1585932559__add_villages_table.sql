-- New Migration
create table addr_villages
(
    id bigint auto_increment
        primary key,
    created datetime null,
    deleted bit not null,
    last_updated datetime null,
    uuid_str varchar(255) null,
    name_bn varchar(255) null,
    name_en varchar(255) null,
    created_by_id bigint null,
    updated_by_id bigint null,
    union_id bigint null,
    ward int null,
    unique_id int not null,
    constraint unique_uuid
        unique (uuid_str),
    constraint village_unique_id
        unique (unique_id),
    constraint FKef4h0d8a1an99tk4ia33wqcod
        foreign key (created_by_id) references m_users (id),
    constraint FKiywuokdgtvypr95gcrgqfhw0v
        foreign key (updated_by_id) references m_users (id),
    constraint FKnavqm8idv3axcj04hum8h41kj
        foreign key (union_id) references addr_unions (id)
);


create table addr_addresses
(
    id bigint auto_increment
        primary key,
    created datetime null,
    deleted bit not null,
    last_updated datetime null,
    uuid_str varchar(255) null,
    lat double null,
    lng double null,
    created_by_id bigint null,
    updated_by_id bigint null,
    district_id bigint null,
    division_id bigint null,
    union_id bigint null,
    upazila_id bigint null,
    village_id bigint null,
    address varchar(255) null,
    constraint unique_uuid
        unique (uuid_str),
    constraint FK57b7qtjb4mo4r1h0pkfkphctq
        foreign key (division_id) references addr_divisions (id),
    constraint FKe60shcg0ov05fxjhu4fpudwdw
        foreign key (created_by_id) references m_users (id),
    constraint FKecu80cylt6t17h67hwx3pdfhb
        foreign key (district_id) references addr_districts (id),
    constraint FKhoawlroyfostm2onjoljatw2t
        foreign key (upazila_id) references addr_upazilas (id),
    constraint FKi4xcl5sms33forhhsy985ajij
        foreign key (union_id) references addr_unions (id),
    constraint FKlhmmwhncf1t18otv1msbj4sbn
        foreign key (updated_by_id) references m_users (id),
    constraint FKt7huwcwtolp40k779q569iyul
        foreign key (village_id) references addr_villages (id)
);

