/*
-- Spring boot initialize DB. It shoud be run manually before any migration can be done --
create database db_example; -- Create the new database
create user 'springuser'@'localhost' identified by 'ThePassword'; -- Creates the user
grant all on db_example.* to 'springuser'@'localhost'; -- Gives all the privileges to the new user on the newly created database
*/

/*
Create DB model to use with default Spring JDBC authentication
Add initial users (listed in format user:password): admin:password, admin_only:password, foo:bar
Don't put any comments between queries (especially SQL comments '-- '), Flyaway may throw MySQL syntax error
*/

create table users(
	username varchar(50) not null primary key,
	password char(60) not null,
	enabled tinyint not null
) ENGINE=InnoDB;
create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
) ENGINE=InnoDB;
create unique index ix_auth_username on authorities (username,authority);

insert into users(username, password, enabled) values
    ('admin', '$2a$10$OluNYAddwrNCrXWO29hRauedtvwHQo13FNQN9LNdZXr8bPBb26P6i', 1),
    ('foo', '$2a$10$rD6X5jXP55wnrM44Pbfk9ux7Fbf5A2e1CDl5TJ0mS8rF/7pBCqLDC', 1),
    ('admin_only', '$2a$10$72RySPhRTCuuoLGmUPsJ4.BqAiX.5b5zLNJnFQhPhAf9WK79cbdqO', 1);

insert into authorities(username, authority) values
    ('admin', 'ROLE_ADMIN'),
    ('admin', 'ROLE_USER'),
    ('foo', 'ROLE_USER'),
    ('admin_only', 'ROLE_ADMIN');
