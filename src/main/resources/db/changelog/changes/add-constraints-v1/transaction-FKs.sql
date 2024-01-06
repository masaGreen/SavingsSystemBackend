--liquibase formatted sql

--changeset masagGreen:9

alter table if exists transaction 
add constraint FK6g20fcr3bhr6bihgy24rq1r1b 
foreign key (account_id) 
references account;