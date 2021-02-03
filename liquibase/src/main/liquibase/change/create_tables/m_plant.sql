create table if not exists m_plant
(
    id integer not null
        constraint pk_plant
            primary key,
    id_from_device integer not null,
    name varchar(1000) not null,
    description varchar(3000),
    kingdom_type varchar(50) not null,
    file_path varchar(500),
    created_date timestamp,
    user_id integer not null,
    coordinate varchar(100),

    CONSTRAINT pk_plant_user_id UNIQUE (id_from_device, user_id),
    CONSTRAINT pk_plant_file_path UNIQUE (file_path)
);

create sequence seq_plant;

comment on table m_plant is 'Растения/Грибы';

comment on column m_plant.id is 'Идентификатор';

comment on column m_plant.id_from_device is 'Идентификатор записи с устройства пользователя';

comment on column m_plant.name is 'Название';

comment on column m_plant.description is 'Описание';

comment on column m_plant.kingdom_type is 'Тип царства, к которому относится сущность (грибы, растения)';

comment on column m_plant.file_path is 'Путь к файлу в файловом хранилище';

comment on column m_plant.created_date is 'Дата добавления сущности в базу';

comment on column m_plant.user_id is 'Идентификатор пользователя, от которого пришла сущность';

comment on column m_plant.coordinate is 'Географические координаты местонахождения сущности';

