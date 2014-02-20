drop table if exists users;

create table users (
	id int not null auto_increment,
	name varchar(20) not null,
	password varchar(64) not null,
	is_admin tinyint default 0 not null,
	primary key (id),
	unique key (name)
) engine=InnoDB default charset=utf8;

insert into users (name, password, is_admin) values ("fachenglin", md5("fachenglin"), 1);
insert into users (name, password, is_admin) values ("magicyuli", md5("magicyuli"), 0);

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

drop procedure if exists cramNews;

create procedure cramNews(j int)
begin
	declare i int;
	set i = 0;
	while i < j do
		insert into news (title, content, publish_time, publish_user_id, last_update_user_id) select title, content, publish_time, publish_user_id, last_update_user_id from news limit 1;
		set i = i + 1;
	end while;
end;