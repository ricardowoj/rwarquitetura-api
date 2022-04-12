CREATE TABLE `tab_cliente_secundario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `dh_cadastro` datetime NOT NULL,
  `estado` varchar(255) NOT NULL,
  `id_usuario` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `numero_doc` varchar(255) NOT NULL,
  `rua` varchar(255) NOT NULL,
  `tipo_documento` int NOT NULL,
  `id_arquiteto` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91ky5kav9ooooayw57arwvbci` (`id_arquiteto`),
  CONSTRAINT `FK91ky5kav9ooooayw57arwvbci` FOREIGN KEY (`id_arquiteto`) REFERENCES `tab_arquiteto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci