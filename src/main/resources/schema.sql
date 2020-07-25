drop table if exists user_ord;
drop table if exists ord_product;
drop table if exists authorities;
drop table if exists user;
drop table if exists ord;
drop table if exists product;


CREATE TABLE IF NOT EXISTS user
(
id bigint not null primary key auto_increment,
name varchar(80),
surname varchar(45),
username varchar(45) unique,
email varchar(80) unique,
password varchar(100),
enabled boolean default true
);

create table if not exists authorities 
(

username varchar(45),
authority varchar(45)
);
alter table authorities add constraint authorities_fk foreign key (username) references user (username) on delete cascade;

create table if not exists ord
(
id bigint not null primary key auto_increment,
date datetime,
address varchar(90),
active boolean,
total float
);

CREATE TABLE IF NOT EXISTS user_ord(
username varchar(45) not null,
ord_id bigint not null
);
alter table user_ord add constraint user_ord_pk primary key (username, ord_id);
alter table user_ord add constraint user_ord_fk1 foreign key (username) references user (username) on delete cascade;
alter table user_ord add constraint user_ord_fk2 foreign key (ord_id) references ord (id) on delete cascade;

create table if not exists product
(
id bigint not null primary key auto_increment,
name varchar(45),
unit varchar(45),
price float,
img varchar(45)
);

create table if not exists ord_product
(
ord_id bigint not null,
product_id bigint not null
);

alter table ord_product add constraint ord_product_pk primary key (ord_id, product_id);
alter table ord_product add constraint ord_product_fk1 foreign key (ord_id) references ord (id) on delete cascade;
alter table ord_product add constraint ord_product_fk2 foreign key (product_id) references product (id) on delete cascade;

