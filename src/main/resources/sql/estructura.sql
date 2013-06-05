DROP DATABASE if exists reservas;
CREATE DATABASE reservas DEFAULT CHARACTER SET utf8 ;

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
		ON DELETE CASCADE ON UPDATE CASCADE
);

-- Recurso final si is_group=false
-- parent == null si no pertenece a ningún grupo
-- CREATE TABLE resources(
-- 	id MEDIUMINT AUTO_INCREMENT,
-- 	name VARCHAR(50) NOT NULL UNIQUE,
-- 	is_group BOOLEAN DEFAULT false,
-- 	parent MEDIUMINT,
-- 	level TINYINT NOT NULL,
-- 	PRIMARY KEY (id),
-- 	FOREIGN KEY (parent) REFERENCES resources(id)
-- );


-- CREATE TABLE final_resources(
-- 	id MEDIUMINT AUTO_INCREMENT,
-- 	description VARCHAR(250),
-- 	quantity SMALLINT,
-- 	PRIMARY KEY (id),
-- 	FOREIGN KEY (id) REFERENCES resources(id)
-- 		ON DELETE CASCADE
-- );

-- ---
CREATE TABLE resources_groups(
	name VARCHAR(50),
	PRIMARY KEY (name)
);


CREATE TABLE resources(
	id MEDIUMINT AUTO_INCREMENT,
	description VARCHAR(250),
	quantity SMALLINT,
        group_name VARCHAR(50),
	PRIMARY KEY (id),
	FOREIGN KEY (gourp_name) REFERENCES resources_groups(name)
		ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table reservation
-- -----------------------------------------------------
CREATE TABLE reservations (
	id INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(250) NULL,
	owner_id MEDIUMINT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	repetition_type VARCHAR(20) COMMENT 'monthly, weekly, daily, once, monthly_relative' ,
	repetition_interval TINYINT,
	PRIMARY KEY (id),
	FOREIGN KEY (owner_id) REFERENCES users(id) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reservation_weekdays(
	reservation_id INT,
	week_day TINYINT,
	PRIMARY KEY (reservation_id, week_day),
	FOREIGN KEY (reservation_id) REFERENCES reservations(id) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table scheduling_reservation
-- -----------------------------------------------------
CREATE TABLE scheduling_reservations (
	id INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(250) NULL,
	owner_id MEDIUMINT NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (owner_id) REFERENCES users(id) 
		ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table reservation_instance
-- -----------------------------------------------------
CREATE TABLE reservation_instances (
	id INT AUTO_INCREMENT ,
	reservation_id INT NOT NULL ,
	start_time_date DATETIME NOT NULL,
	end_time_date DATETIME NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (reservation_id) REFERENCES reservations (id)
		ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table reserved_resources
-- -----------------------------------------------------
CREATE TABLE reserved_resources (
	resource_id MEDIUMINT,
	reservation_id INT,
	PRIMARY KEY (resource_id, reservation_id),
	FOREIGN KEY (resource_id) REFERENCES resources (id),
	FOREIGN KEY (reservation_id) REFERENCES reservations (id)
);


-- -----------------------------------------------------
-- Table invitations
-- -----------------------------------------------------
CREATE TABLE invitations (
	user_id MEDIUMINT,
	reservation_id INT ,
	state TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY (user_id, reservation_id) ,
	FOREIGN KEY (reservation_id) REFERENCES reservations (id),
	FOREIGN KEY (user_id) REFERENCES users (id)
);


-- -----------------------------------------------------
-- Triggers
-- -----------------------------------------------------
DROP TRIGGER fixed_reservation_once_creation;

CREATE TRIGGER fixed_reservation_once_creation BEFORE INSERT ON fixed_reservations
FOR EACH ROW 
	SET NEW.end_date = IF(ISNULL(NEW.end_date), NEW.start_date, NEW.end_date);