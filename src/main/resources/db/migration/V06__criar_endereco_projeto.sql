CREATE TABLE `tab_endereco_projeto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `dh_cadastro` datetime NOT NULL,
  `estado` varchar(255) NOT NULL,
  `id_cliente_secundario` int NOT NULL,
  `numero` varchar(255) NOT NULL,
  `rua` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci