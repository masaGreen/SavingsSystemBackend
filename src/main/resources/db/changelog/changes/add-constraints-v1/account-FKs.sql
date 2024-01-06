--liquibase formatted sql

--changeset masaGreen:7

alter table if exists account 
add constraint FKgw84mgpacw9htdxcs2j1p7u6j 
foreign key (account_type_id) 
references account_type;

alter table if exists account 
add constraint FKafrffflgfjs50vsxmq6v1qdev 
foreign key (app_user_id) 
references app_user;