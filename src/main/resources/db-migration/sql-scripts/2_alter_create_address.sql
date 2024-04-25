alter table if exists contact drop column if exists city;
drop table if exists contact_address;
create table contact_address(
    id uuid primary key,
    city varchar(255),
    state varchar(255),
    country varchar(255),
    zip_code varchar(255),
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    deleted boolean default false
);
alter table if exists contact add column address_id uuid references contact_address(id);