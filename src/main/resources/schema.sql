create table if not exists product (
id int primary key auto_increment,
name varchar(50) not null unique,
price float not null
);