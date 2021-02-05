create table if not exists M_USER
(
    id integer not null
        constraint pk_user
            primary key,
    device_name varchar(1000) not null,
    name varchar(1000)
);

create sequence seq_user;

comment on table M_USER is 'Таблица для пользователей';

comment on column M_USER.id is 'Идентификатор';

comment on column M_USER.device_name is 'Уникальный ключ устройства';

comment on column M_USER.name is 'Имя пользователя';