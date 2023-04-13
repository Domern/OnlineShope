create table products (
    id bigserial primary key,
    title        varchar(255),
    price        int,
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
                      );
insert into products (title,price) values
('Milk', 80), ('Bread', 25), ('Cheese', 300);
create table users(
    id bigserial primary key,
    username varchar(36) not null,
    password varchar(80) not null
);

create table roles(
    id bigserial primary key,
    name varchar(50) not null
);

create table users_roles(
    user_id bigint not null references users(id),
    role_id bigint not null references roles(id),
    primary key (user_id, role_id)
);

insert into roles(name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users(username,password)
values ('bob','$2a$12$QiirktVkw6NnoLtx3W9y1e7jvhNmmGpwTExN9v9w3DfsGf9i.wquy'),
       ('john','$2a$12$QiirktVkw6NnoLtx3W9y1e7jvhNmmGpwTExN9v9w3DfsGf9i.wquy');

insert into users_roles(user_id, role_id)
values(1,1),
       (2,2);