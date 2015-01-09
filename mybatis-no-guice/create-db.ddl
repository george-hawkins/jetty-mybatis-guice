CREATE TABLE users (
  user_id SERIAL,
  email_id varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) default NULL,
  PRIMARY KEY  (user_id),
  UNIQUE (email_id)
);
-- cat create-db.ddl | heroku pg:psql
