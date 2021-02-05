create table if not exists M_COMMENT
(
    id integer not null
        constraint pk_comment
            primary key,
    plant_id integer not null
        constraint fk1_comment
            references m_plant,
    comment varchar(4000) not null,
    user_id integer not null,
    created_date timestamp not null
);

create sequence seq_comment;

comment on table M_COMMENT is 'Таблица для хранения комментариев к сущности plant';

comment on column M_COMMENT.plant_id is 'Идентификатор сущности plant';

comment on column M_COMMENT.comment is 'Комментарий к сущности plant';

comment on column M_COMMENT.user_id is 'Идентификатор пользователя';

comment on column M_COMMENT.created_date is 'Дата создания';

