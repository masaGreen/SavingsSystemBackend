--liquibase formatted sql

--changeset masaGreen:5

create table role (
    created_at timestamp(6) with time zone, 
    updated_at timestamp(6) with time zone, 
    id varchar(255) not null, 
    name varchar(255), 
    primary key (id));