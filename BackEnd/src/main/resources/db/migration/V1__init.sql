create table "Users" (
    id uuid primary key,
    name text not null,
    phone varchar(11) not null unique,
    email text not null unique,
    created_at timestamp not null,
    deleted_at timestamp
);