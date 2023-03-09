create table account(
	id varchar(255) primary key,
    username varchar(255),
    pass varchar(64),
    secession_confirmed bool
);

create table mailbox(
	id varchar(64) primary key,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sender varchar(255) not null,
    receiver varchar(255),
    title varchar(512) not null default "No Title",
    main_text text,
    file_path json,
    garbage_confirmed bool,
    delete_confirmed bool
);

