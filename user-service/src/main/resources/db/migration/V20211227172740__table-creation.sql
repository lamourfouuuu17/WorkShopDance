create schema if not exists workshop;
create table if not exists workshop.users
(
    id       bigserial
        constraint users_pk
            primary key,
    name     text,
    surname  text,
    email    text,
    password text,
    role     text
);

create table if not exists workshop.lessons
(
    id         bigserial,
    student_id integer,
    teacher_id integer,
    date       timestamp
);

create unique index if not exists lessons_id_uindex
    on workshop.lessons (id);

create table if not exists workshop.goods
(
    id       bigserial,
    name     text,
    price    double precision,
    category text
);

create unique index if not exists goods_id_uindex
    on workshop.goods (id);

drop view if exists workshop.lessons_full_view;

create view lessons_full_view(id, date, name, surname, email, teacher_email) as
SELECT l.id,
       l.date,
       u.name,
       u.surname,
       u.email,
       (SELECT u2.email
        FROM workshop.users u2
        WHERE l.teacher_id = u2.id) AS teacher_email
FROM workshop.lessons l
         LEFT JOIN workshop.users u ON u.id = l.student_id;