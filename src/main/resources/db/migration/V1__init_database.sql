create table if not exists product (
    id uuid not null primary key,
    article varchar(255) not null unique,
    name varchar(255) not null,
    description varchar(512) not null,
    category varchar(255) not null,
    price numeric(10,2) not null,
    quantity integer not null,
    last_quantity_change_date timestamp(6),
    creation_date timestamp(6) not null
)