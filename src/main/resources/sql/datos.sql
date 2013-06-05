INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('1', 'tic');
INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('2', 'user');

INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`) VALUES ('1','admin', 'Todopoderoso', 'admin@gmail.com');
INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`) VALUES ('2','perico', 'Perico Palotes', 'periquito@gmail.com');

INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('2', '2');



-- Recursos de ejemplo:
INSERT INTO `reservas`.`resources` (`id`, `name`, `is_group`, `parent`, `level`) VALUES ('1', 'grupo de ejemplo', '1', NULL, 0);
INSERT INTO `reservas`.`resources` (`id`, `name`, `is_group`, `parent`, `level`) VALUES ('2', 'subgrupo de ejemplo', '1', '1','1');
INSERT INTO `reservas`.`resources` (`id`, `name`, `parent`, `level`) VALUES ('3', 'Ejemplo recurso', '2','2');
INSERT INTO `reservas`.`resources` (`id`, `name`, `parent`, `level`) VALUES ('4', 'Ejemplo recurso again', '2','2');

INSERT INTO `reservas`.`final_resources` (`id`, `description`, `quantity`) VALUES ('3', 'Descripción de ejemplo', '8');
INSERT INTO `reservas`.`final_resources` (`id`, `description`, `quantity`) VALUES ('4', 'Descripción de ejemplo again', '10');
