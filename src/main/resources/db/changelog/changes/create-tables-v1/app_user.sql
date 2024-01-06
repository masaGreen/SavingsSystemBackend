--liquibase formatted sql

--changeset masaGreen:3

create table app_user (
    is_verified boolean not null, 
    created_at timestamp(6) with time zone, 
    updated_at timestamp(6) with time zone, 
    email varchar(255), first_name varchar(255), 
    id varchar(255) not null, 
    id_number varchar(255) unique, 
    last_name varchar(255), 
    phone_number varchar(255), 
    pin varchar(255), 
    pin_encryption varchar(255), 
    validation_string varchar(255), 
    primary key (id));