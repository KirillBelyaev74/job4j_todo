create database job4j_todo;
drop database job4j_todo;

create table customer(
    id serial primary key,
    login varchar not null unique,
    password varchar not null);
drop table customer;

create table item(
    id          serial primary key,
    description text      not null,
    created     timestamp not null,
    done        boolean   not null,
    id_customer int references customer(id));
drop table item;