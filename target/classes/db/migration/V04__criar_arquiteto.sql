CREATE TABLE `tab_arquiteto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `dh_cadastro` datetime DEFAULT NULL,
  `estado` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `numero_doc` varchar(255) NOT NULL,
  `rua` varchar(255) NOT NULL,
  `id_tipo_documento` int NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiwtoay0ids97cii5ux81ugs1t` (`id_usuario`),
  CONSTRAINT `FKiwtoay0ids97cii5ux81ugs1t` FOREIGN KEY (`id_usuario`) REFERENCES `tab_usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci