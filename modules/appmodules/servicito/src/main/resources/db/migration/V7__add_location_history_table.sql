-- auto-generated definition
create table location_history
(
    id            bigint auto_increment
        primary key,
    created       datetime     null,
    deleted       bit          not null,
    last_updated  datetime     null,
    uuid_str      varchar(255) null,
    latitude      double       not null,
    longitude     double       not null,
    reference     varchar(255) null,
    created_by_id bigint       null,
    updated_by_id bigint       null,
    user_id       bigint       null,
    constraint FK5lprtl124trlqh1w5tq59hlm2
        foreign key (created_by_id) references m_users (id),
    constraint FK81rjnoa8i38dik196o281vspa
        foreign key (updated_by_id) references m_users (id),
    constraint FK9xx2net0txq86tk7dfcdafetb
        foreign key (user_id) references m_users (id)
);

