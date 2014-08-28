DROP DATABASE IF EXISTS java_db;

CREATE DATABASE java_db;
USE java_db;

CREATE TABLE User(
	userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	login VARCHAR(50) NOT NULL,
    passw VARCHAR(32) NOT NULL,
	created TIMESTAMP DEFAULT NOW()
);

INSERT INTO User (login, passw) VALUES ('max', md5(11));
INSERT INTO User (login, passw) VALUES ('jen', md5(11));

SELECT * FROM User;