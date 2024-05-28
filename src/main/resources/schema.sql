create table  if not exists users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table if not exists authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index if not exists ix_auth_username on authorities (username,authority);
create table if not exists student(student_id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL primary key,code varchar(500) not null unique,first_name varchar(500) not null, last_name varchar(500) not null, photo varchar(500), program_id varchar(500) not null);
create table if not exists payment(id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL primary key, date varchar(500) not null,amount varchar(500) not null, type varchar(500) not null, status varchar(500), file varchar(500), student_id INTEGER not null REFERENCES student(student_id));