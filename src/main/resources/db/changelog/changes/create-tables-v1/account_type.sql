--liquibase formatted sql

--changeset masaGreen:2

create table account_type (
    interest float(53), 
    minimum_balance numeric(38,2), 
    created_at timestamp(6) with time zone, 
    updated_at timestamp(6) with time zone, 
    id varchar(255) not null, 
    name varchar(255), 
    primary key (id));