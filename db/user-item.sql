create database job4j_todo;
drop database job4j_todo;

create table customer(
    id serial primary key,
    login varchar not null unique,
    password varchar not null);
drop table customer;
select * from customer;

create table item(
    id          serial primary key,
    description text      not null,
    created     timestamp not null,
    done        boolean   not null,
    id_customer int references customer(id));
drop table item;
select * from item;

create table category(
    id serial primary key,
    name varchar not null);
drop table category;
select * from category;

insert into category(name) values ('Работа');
insert into category(name) values ('Дом');
insert into category(name) values ('Медицина');
insert into category(name) values ('Семья');
insert into category(name) values ('Машина');
insert into category(name) values ('Мотоцикл');