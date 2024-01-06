--liquibase formatted sql

--changeset masaGreen:4

create table "appuser-role" (
    app_user_id varchar(255) not null, 
    role_id varchar(255) not null, 
    primary key (app_user_id, role_id));