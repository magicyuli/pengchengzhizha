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


drop procedure if exists cramMessage;

create procedure cramMessage(j int)
begin
	declare i int;
	set i = 0;
	while i < j do
		insert into message (subject, content, sending_time, sender_name, sender_phone, sender_company, sender_email, is_replied, replied_by_user_id) select subject, content, sending_time, sender_name, sender_phone, sender_company, sender_email, is_replied, replied_by_user_id from message limit 1;
		set i = i + 1;
	end while;
end;
