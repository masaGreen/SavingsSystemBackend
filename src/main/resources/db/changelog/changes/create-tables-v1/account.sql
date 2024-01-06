--liquibase formatted sql

--changeset masaGreen:1

 create table account (
    balance numeric(38,2), 
    created_at timestamp(6) with time zone, 
    updated_at timestamp(6) with time zone, 
    account_number varchar(255), 
    account_type_id varchar(255), 
    app_user_id varchar(255), 
    id varchar(255) not null, 
    primary key (id));