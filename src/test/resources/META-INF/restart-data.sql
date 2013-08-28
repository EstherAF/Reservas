TRUNCATE TABLE reservas.reservation_instances, 
               reservas.reserved_resources, 
               reservas.reservation_weekdays, 
               reservas.invitations,
               reservas.reservations,
               reservas.resources,
               reservas.resources_groups,
               reservas."users",
               reservas.roles;

INSERT INTO reservas.roles (name) VALUES ('tic');
INSERT INTO reservas.roles (name) VALUES ('user');

INSERT INTO reservas."users" (unique_name, full_name, email, role) VALUES ('admin', 'Todopoderoso', 'admin@gmail.com', 'tic');
INSERT INTO reservas."users" (unique_name, full_name, email, role) VALUES ('perico', 'Perico Palotes', 'periquito@gmail.com', 'user');

INSERT INTO reservas.resources_groups (id, name) VALUES (1, 'default');
INSERT INTO reservas.resources_groups (id, name, description) VALUES (2,'videoconferencia', 'salas con videoconferencia');

INSERT INTO reservas.resources (description, name) VALUES ('recurso ejemplo', 'recurso');
INSERT INTO reservas.resources (description, name) VALUES ('recurso ejemplo 2', 'recurso2');
INSERT INTO reservas.resources (description, group_id, name) VALUES ('sala de videoconferencia 1', '2', 'sala#01');
INSERT INTO reservas.resources (description, group_id, name) VALUES ('sala de videoconferencia 2', '2', 'sala#02');