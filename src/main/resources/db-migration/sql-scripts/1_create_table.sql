drop table if exists contact;
create table contact(
    id uuid primary key,
    name varchar(255),
    email varchar(255),
    mobile varchar(10),
    city varchar(255),
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    deleted boolean default false
);