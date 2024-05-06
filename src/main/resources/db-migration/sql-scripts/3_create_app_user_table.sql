drop table if exists app_user;
create table app_user(
                         id uuid primary key,
                         username varchar(255) not null,
                         password varchar(255) not null,
                         email varchar(255) not null,
                         mobile varchar(10) not null,
                         created_date timestamp without time zone,
                         modified_date timestamp without time zone,
                         deleted boolean default false
);