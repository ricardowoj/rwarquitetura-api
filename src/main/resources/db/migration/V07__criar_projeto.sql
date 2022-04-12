CREATE TABLE `tab_projeto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dh_cadastro` datetime NOT NULL,
  `id_tipo_projeto` int NOT NULL,
  `vr_projeto` decimal(19,2) NOT NULL,
  `id_endereco_projeto` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdda1cbhkmmt4xdci6xdvvb2t6` (`id_endereco_projeto`),
  CONSTRAINT `FKdda1cbhkmmt4xdci6xdvvb2t6` FOREIGN KEY (`id_endereco_projeto`) REFERENCES `tab_endereco_projeto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci