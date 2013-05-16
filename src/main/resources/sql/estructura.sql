DROP DATABASE if exists reservas;
CREATE DATABASE `reservas` DEFAULT CHARACTER SET utf8 ;

USE reservas;

-- GRANT ALL PRIVILEGES ON reservas.* to 'admin'@'localhost' IDENTIFIED BY 'adminpassword';

CREATE TABLE roles (
	id MEDIUMINT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL UNIQUE, 
	PRIMARY KEY(id)
);

CREATE TABLE users (
        id MEDIUMINT AUTO_INCREMENT,
	unique_name VARCHAR(50) NOT NULL UNIQUE,
        full_name VARCHAR(150) NOT NULL, 
	email VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(id)
);

CREATE TABLE users_roles(
        rol_id MEDIUMINT,
        user_id MEDIUMINT,
        PRIMARY KEY(rol_id, user_id),
        FOREIGN KEY (rol_id) references roles(id),
        FOREIGN KEY (user_id) references users(id)
            ON DELETE CASCADE 
            ON UPDATE CASCADE
);
