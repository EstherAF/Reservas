INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('1', 'tic');
INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('2', 'user');

INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`) VALUES ('1','admin', 'Todopoderoso', 'admin@gmail.com');
INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`) VALUES ('2','perico', 'Perico Palotes', 'periquito@gmail.com');

INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('2', '2');



-- Recursos de ejemplo:
INSERT INTO `reservas`.`resources_groups` (`name`) VALUES ('default');
INSERT INTO `reservas`.`resources_groups` (`name`, `description`) VALUES ('videoconferencia', 'salas con videoconferencia');

INSERT INTO `reservas`.`resources` (`description`, `name`) VALUES ('recurso ejemplo', 'recurso');
INSERT INTO `reservas`.`resources` (`description`, `quantity`, `name`) VALUES ('recurso ejemplo 2', '5', 'recurso2');
INSERT INTO `reservas`.`resources` (`description`, `quantity`, `group_id`, `name`) VALUES ('sala de videoconferencia v1', '1', '2', 'sala v1');