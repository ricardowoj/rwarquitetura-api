CREATE TABLE `tab_levantamento_medicao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `caminho_arquivo` varchar(255) DEFAULT NULL,
  `dh_cadastro` datetime DEFAULT NULL,
  `dh_trabalhada_fim` datetime DEFAULT NULL,
  `dh_trabalhada_inicio` datetime DEFAULT NULL,
  `fl_arquivo_presente` bit(1) DEFAULT NULL,
  `id_arquieto` int DEFAULT NULL,
  `id_cliente_secundario` int DEFAULT NULL,
  `nome_arquivo` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `tamanho_arquivo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci