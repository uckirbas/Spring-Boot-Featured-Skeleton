-- New Migration
create table roles_privileges
(
    role_id bigint not null,
    privilege_id bigint not null,
    constraint FK5duhoc7rwt8h06avv41o41cfy
        foreign key (privilege_id) references privileges (id),
    constraint FK629oqwrudgp5u7tewl07ayugj
        foreign key (role_id) references roles (id)
);

