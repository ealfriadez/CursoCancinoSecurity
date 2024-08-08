CREATE TABLE `usuarios` (
 `id` int unsigned NOT NULL AUTO_INCREMENT,
 `nombre` varchar(100) NOT NULL,
 `correo` varchar(100) NOT NULL,
 `telefono` varchar(100) NOT NULL,
 `password` varchar(100) NOT NULL,
 `estado` int NOT NULL,
 PRIMARY KEY (`id`)
)

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `telefono`, `password`, `estado`) VALUES
(1, 'César Cancino', 'info@tamila.cl', '0980980', '$2a$10$yqmC/dTTbbe9fKVN2g.SuOngaXHy288VHwrj0oF.Rsz1U5/LrT4PG', 1),
(2, 'Yelimar Díaz', 'marimar@tamila.cl', '98798', '$2a$10$yqmC/dTTbbe9fKVN2g.SuOngaXHy288VHwrj0oF.Rsz1U5/LrT4PG', 1);

CREATE TABLE `autorizar` (
 `id` int unsigned NOT NULL AUTO_INCREMENT,
 `usuarios_id` int unsigned NOT NULL,
 `nombre` varchar(100) NOT NULL,
 PRIMARY KEY (`id`),
 KEY `fk_usuarios_id` (`usuarios_id`),
 CONSTRAINT `fk_usuarios_id` FOREIGN KEY (`usuarios_id`) REFERENCES `usuarios` (`id`)
)

INSERT INTO `autorizar` (`id`, `usuarios_id`, `nombre`) VALUES
(1, 1, 'ROLE_ADMIN'),
(2, 1, 'ROLE_USER'),
(3, 2, 'ROLE_USER');
