DROP table if exists person;

CREATE table person
(
	person_id serial primary key,
	last_name varchar(255),
	first_name varchar(255),
	email varchar(255),
	age int
);