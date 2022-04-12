CREATE TABLE `tab_parcela_pagamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dh_cadastro` datetime NOT NULL,
  `data_pagamento` tinyblob NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  `id_projeto` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdkaag0p9pgln9rmpvypfvye7e` (`id_projeto`),
  CONSTRAINT `FKdkaag0p9pgln9rmpvypfvye7e` FOREIGN KEY (`id_projeto`) REFERENCES `tab_projeto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci