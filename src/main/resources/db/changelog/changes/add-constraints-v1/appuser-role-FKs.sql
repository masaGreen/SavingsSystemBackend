--liquibase formatted sql

--changeset masaGreen:8

alter table if exists "appuser-role" 
add constraint FK5fr9cwn6mw7olpf800xecyqdq 
foreign key (role_id) 
references role;

alter table if exists "appuser-role" 
add constraint FKp6gvcy0vv56d13msnpto3qf5w 
foreign key (app_user_id) 
references app_user;

