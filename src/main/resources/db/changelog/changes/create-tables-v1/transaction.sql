--liquibase formatted sql

--changeset masaGreen:6

create table transaction (
    amount numeric(38,2), 
    transaction_charge float(53), 
    created_at timestamp(6) with time zone, 
    updated_at timestamp(6) with time zone, 
    account_id varchar(255), 
    id varchar(255) not null, 
    transaction_medium varchar(255) check (transaction_medium in ('WALK_IN','MPESA','ATM')), 
    transaction_type varchar(255) check (transaction_type in ('DEPOSIT','WITHDRAWAL','BALANCE_CHECK')), 
    primary key (id));