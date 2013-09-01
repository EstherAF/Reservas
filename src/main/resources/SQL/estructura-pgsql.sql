DROP SCHEMA IF EXISTS reservas CASCADE;
DROP DATABASE IF EXISTS reservas;
DROP ROLE IF EXISTS admin;

CREATE ROLE admin LOGIN PASSWORD 'adminpassword' VALID UNTIL 'infinity';
CREATE DATABASE reservas WITH ENCODING='UTF8' OWNER=admin CONNECTION LIMIT=-1;
CREATE SCHEMA "reservas" AUTHORIZATION admin;

-- CREATE TABLE reservas.roles (
-- 	name VARCHAR(50), 
-- 	PRIMARY KEY(name))
-- ;

CREATE TABLE reservas.users (
	unique_name VARCHAR(50),
	full_name VARCHAR(150) NOT NULL, 
	email VARCHAR(100) NOT NULL,
        --role VARCHAR(50) NOT NULL DEFAULT 1,
	PRIMARY KEY(unique_name),
--         FOREIGN KEY (role) references reservas.roles(name)
--             ON UPDATE CASCADE
);

CREATE TABLE reservas.resources_groups(
        id SERIAL,
	name VARCHAR(100) UNIQUE NOT NULL,
        description TEXT,
	PRIMARY KEY (id)
);


CREATE TABLE reservas.resources(
	id SERIAL,
        name VARCHAR(100) NOT NULL UNIQUE,
	description TEXT,
        group_id INTEGER DEFAULT 1,
	PRIMARY KEY (id),
	FOREIGN KEY (group_id) references reservas.resources_groups(id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table reservation
-- -----------------------------------------------------
CREATE TABLE reservas.reservations (
	id SERIAL,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL,
	owner VARCHAR(50) NOT NULL,
	start_time_date TIMESTAMP NOT NULL,
	end_time_date TIMESTAMP NOT NULL,
	repetition_type VARCHAR(20) NOT NULL DEFAULT 'once',
	repetition_interval INTEGER,
        repetition_end_date DATE,
	PRIMARY KEY (id),
	FOREIGN KEY (owner) references reservas.users(unique_name) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reservas.reservation_weekdays(
	reservation_id INTEGER,
	week_day SMALLINT,
	PRIMARY KEY (reservation_id, week_day),
	FOREIGN KEY (reservation_id) references reservas.reservations(id) 
		ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table scheduling_reservation
-- -----------------------------------------------------
--CREATE TABLE reservas.scheduling_reservations (
--	id INTEGER AUTO_INCREMENT,
--	name VARCHAR(50) NOT NULL,
--	description TEXT NULL,
--	owner VARCHAR(50) NOT NULL,
--	PRIMARY KEY (id),
--	FOREIGN KEY (owner) references reservas.users(unique_name) 
--		ON DELETE CASCADE ON UPDATE CASCADE
--);


-- -----------------------------------------------------
-- Table reservation_instance
-- -----------------------------------------------------
CREATE TABLE reservas.reservation_instances (
	id SERIAL,
	reservation_id INTEGER NOT NULL ,
	start_time_date TIMESTAMP NOT NULL,
	end_time_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (reservation_id) references reservas.reservations (id)
		ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table reserved_resources
-- -----------------------------------------------------
CREATE TABLE reservas.reserved_resources (
	resource_id INTEGER,
	reservation_id INTEGER,
	PRIMARY KEY (resource_id, reservation_id),
	FOREIGN KEY (resource_id) references reservas.resources (id)
            ON UPDATE CASCADE,
	FOREIGN KEY (reservation_id) references reservas.reservations (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table invitations
-- -----------------------------------------------------
CREATE TABLE reservas.invitations (
	"user" VARCHAR(50),
	reservation_id INTEGER,
	state VARCHAR(10) NOT NULL DEFAULT 0,
	PRIMARY KEY ("user", reservation_id) ,
	FOREIGN KEY (reservation_id) references reservas.reservations (id),
	FOREIGN KEY ("user") references reservas.users (unique_name)
);


-- -----------------------------------------------------
-- Triggers
-- -----------------------------------------------------
-- DROP TRIGGER fixed_reservation_once_creation;

-- CREATE TRIGGER fixed_reservation_once_creation BEFORE INSERT ON fixed_reservations
-- FOR EACH ROW 
--	SET NEW.end_date = IF(ISNULL(NEW.end_date), NEW.start_date, NEW.end_date);





INSERT INTO reservas.roles (name) VALUES ('tic');
INSERT INTO reservas.roles (name) VALUES ('user');

INSERT INTO reservas."users" (unique_name, full_name, email, role) VALUES ('admin', 'Todopoderoso', 'admin@gmail.com', 'tic');
INSERT INTO reservas."users" (unique_name, full_name, email, role) VALUES ('perico', 'Perico Palotes', 'periquito@gmail.com', 'user');

-- Recursos de ejemplo:
INSERT INTO reservas.resources_groups (name) VALUES ('default');
INSERT INTO reservas.resources_groups (name, description) VALUES ('videoconferencia', 'salas con videoconferencia');

INSERT INTO reservas.resources (description, name) VALUES ('recurso ejemplo', 'recurso');
INSERT INTO reservas.resources (description, name) VALUES ('recurso ejemplo 2', 'recurso2');
INSERT INTO reservas.resources (description, group_id, name) VALUES ('sala de videoconferencia 1', '2', 'sala#01');
INSERT INTO reservas.resources (description, group_id, name) VALUES ('sala de videoconferencia 2', '2', 'sala#02');