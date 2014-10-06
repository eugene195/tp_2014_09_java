DROP DATABASE IF EXISTS g01_java_db;

GRANT ALL PRIVILEGES ON g01_java_db.* TO g01_user IDENTIFIED BY 'drovosek';

USE g01_java_db;

CREATE TABLE User(
	userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	login VARCHAR(50) NOT NULL,
    passw VARCHAR(32) NOT NULL,
    score INT NOT NULL,
	created TIMESTAMP DEFAULT NOW()
);

INSERT INTO User (login, passw, score) VALUES ('max', md5(11), 100);
INSERT INTO User (login, passw, score) VALUES ('jen', md5(11), 80);

INSERT INTO User (login, passw, score) VALUES ('anton', md5(11), 200);
INSERT INTO User (login, passw, score) VALUES ('sergey', md5(11), 50);

INSERT INTO User (login, passw, score) VALUES ('andrey', md5(11), 300);
INSERT INTO User (login, passw, score) VALUES ('diman', md5(11), 20);

INSERT INTO User (login, passw, score) VALUES ('mix', md5(11), 120);
INSERT INTO User (login, passw, score) VALUES ('olga', md5(11), 90);

INSERT INTO User (login, passw, score) VALUES ('oleg', md5(11), 180);
INSERT INTO User (login, passw, score) VALUES ('natasha', md5(11), 90);

INSERT INTO User (login, passw, score) VALUES ('stepan', md5(11), 190);
INSERT INTO User (login, passw, score) VALUES ('alena', md5(11), 70);

SELECT * FROM User;