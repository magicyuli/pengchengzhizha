drop table if exists users;

create table users (
	id int not null auto_increment,
	name varchar(20) not null,
	password varchar(64) not null,
	email varchar(50) not null,
	is_admin tinyint default 0 not null,
	is_activated tinyint default 0 not null,
	is_news_manager tinyint default 0 not null,
	is_message_manager tinyint default 0 not null,
	hash varchar(10),
	primary key (id),
	unique key (name),
	unique key (email)
) engine=InnoDB default charset=utf8;

insert into users (name, password, email, is_admin, is_activated, is_news_manager, is_message_manager) values ("fachenglin", md5("fachenglin"), '340082230@qq.com', 1, 1, 1, 1);
insert into users (name, password, email, is_admin, is_activated, is_news_manager, is_message_manager) values ("magicyuli", md5("magicyuli"), 'magicyuli@gmail.com', 0, 1, 0, 0);

drop table if exists news;

create table news (
	id int not null auto_increment,
	title varchar(50) not null,
	content varchar(21772) not null,
	publish_user_id int not null,
	last_update_user_id int not null,
	last_update_time timestamp not null,
	publish_time timestamp not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

drop table if exists message;

create table message (
	id int not null auto_increment,
	subject varchar(45) not null,
	content varchar(10809) not null,
	sender_name varchar(30) not null,
	sender_company varchar(50),
	sender_email varchar(50) not null,
	sender_phone varchar(30),
	sending_time timestamp not null default CURRENT_TIMESTAMP,
	is_replied tinyint default 0 not null,
	replying_user_id int,
	replying_time timestamp,
	reply_content varchar(10808),
	primary key (id)
) engine=InnoDB default charset=utf8;

