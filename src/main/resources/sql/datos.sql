INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('1', 'tic');
INSERT INTO `reservas`.`roles` (`id`, `name`) VALUES ('2', 'user');

INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`) VALUES ('1','admin', 'Todopoderoso', 'admin@gmail.com');
INSERT INTO `reservas`.`users` (`id`,`unique_name`, `full_name`, `email`, `rol_id`) VALUES ('2','perico', 'Perico Palotes', 'periquito@gmail.com');

INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `reservas`.`users_roles` (`rol_id`, `user_id`) VALUES ('2', '2');