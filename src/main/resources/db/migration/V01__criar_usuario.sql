CREATE TABLE `tab_usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dh_cadastro` datetime DEFAULT NULL,
  `dh_ultimo_acesso` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `id_tipo_status` int DEFAULT NULL,
  `id_tipo_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4d3upqu9ijb9qjnbqbp06p1xg` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci