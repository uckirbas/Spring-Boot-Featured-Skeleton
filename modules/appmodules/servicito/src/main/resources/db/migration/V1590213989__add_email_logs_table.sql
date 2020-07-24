-- New Migration
create table email_logs
(
    id bigint auto_increment
        primary key,
    created datetime null,
    deleted bit not null,
    last_updated datetime null,
    uuid_str varchar(255) null,
    mail_bcc varchar(511) null,
    mail_cc varchar(511) null,
    mail_from varchar(255) not null,
    message text null,
    no_of_attachments int null,
    subject varchar(511) null,
    mail_to varchar(255) not null,
    created_by_id bigint null,
    updated_by_id bigint null
)
    engine=MyISAM;

create index FK27yefjbmccfc0auw49wkswhq6
    on email_logs (updated_by_id);

create index FKtjd72mcrt0g9pbghooqrop8np
    on email_logs (created_by_id);

