DROP DATABASE if exists reservas;
CREATE DATABASE reservas DEFAULT CHARACTER SET utf8 ;

USE reservas;

-- GRANT ALL PRIVILEGES ON reservas.* to 'admin'@'localhost' IDENTIFIED BY 'adminpassword';

CREATE TABLE roles (
	name VARCHAR(50), 
	PRIMARY KEY(name))
;

CREATE TABLE users (
	unique_name VARCHAR(50),
	full_name VARCHAR(150) NOT NULL, 
	email VARCHAR(50) NOT NULL UNIQUE,
        role VARCHAR(50) NOT NULL,
	PRIMARY KEY(unique_name),
        FOREIGN KEY (role) references roles(name)
            ON UPDATE CASCADE
);

CREATE TABLE resources_groups(
        id MEDIUMINT AUTO_INCREMENT,
	name VARCHAR(100) UNIQUE NOT NULL,
        description VARCHAR(250),
	PRIMARY KEY (id)
);


CREATE TABLE resources(
	id MEDIUMINT AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(250),
        group_id MEDIUMINT DEFAULT 1,
	PRIMARY KEY (id),
	FOREIGN KEY (group_id) REFERENCES resources_groups(id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table reservation
-- -----------------------------------------------------
CREATE TABLE reservations (
	id INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(250) NULL,
	owner VARCHAR(50) NOT NULL,
	start_time_date TIMESTAMP NOT NULL,
	end_time_date TIMESTAMP NOT NULL,
	repetition_type VARCHAR(20) COMMENT 'monthly, weekly, daily, once, monthly_relative' ,
	repetition_interval TINYINT,
        repetition_end_date DATE,
	PRIMARY KEY (id),
	FOREIGN KEY (owner) REFERENCES users(unique_name) 
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
	owner VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (owner) REFERENCES users(unique_name) 
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
	FOREIGN KEY (resource_id) REFERENCES resources (id)
            ON UPDATE CASCADE,
	FOREIGN KEY (reservation_id) REFERENCES reservations (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table invitations
-- -----------------------------------------------------
CREATE TABLE invitations (
	user VARCHAR(50),
	reservation_id INT ,
	state VARCHAR(10) NOT NULL DEFAULT 0,
	PRIMARY KEY (user, reservation_id) ,
	FOREIGN KEY (reservation_id) REFERENCES reservations (id),
	FOREIGN KEY (user) REFERENCES users (unique_name)
);


-- -----------------------------------------------------
-- Triggers
-- -----------------------------------------------------
-- DROP TRIGGER fixed_reservation_once_creation;

-- CREATE TRIGGER fixed_reservation_once_creation BEFORE INSERT ON fixed_reservations
-- FOR EACH ROW 
--	SET NEW.end_date = IF(ISNULL(NEW.end_date), NEW.start_date, NEW.end_date);





INSERT INTO `reservas`.`roles` (`name`) VALUES ('tic');
INSERT INTO `reservas`.`roles` (`name`) VALUES ('user');

INSERT INTO `reservas`.`users` (`unique_name`, `full_name`, `email`, `role`) VALUES ('admin', 'Todopoderoso', 'admin@gmail.com', 'tic');
INSERT INTO `reservas`.`users` (`unique_name`, `full_name`, `email`, `role`) VALUES ('perico', 'Perico Palotes', 'periquito@gmail.com', 'user');

-- Recursos de ejemplo:
INSERT INTO `reservas`.`resources_groups` (`name`) VALUES ('default');
INSERT INTO `reservas`.`resources_groups` (`name`, `description`) VALUES ('videoconferencia', 'salas con videoconferencia');

INSERT INTO `reservas`.`resources` (`description`, `name`) VALUES ('recurso ejemplo', 'recurso');
INSERT INTO `reservas`.`resources` (`description`, `name`) VALUES ('recurso ejemplo 2', 'recurso2');
INSERT INTO `reservas`.`resources` (`description`, `group_id`, `name`) VALUES ('sala de videoconferencia 1', '2', 'sala#01');
INSERT INTO `reservas`.`resources` (`description`, `group_id`, `name`) VALUES ('sala de videoconferencia 2', '2', 'sala#02');