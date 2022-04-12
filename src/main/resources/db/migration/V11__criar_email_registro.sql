CREATE TABLE `tab_email_registro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dh_envio` datetime NOT NULL,
  `email_usuario_destinatario` varchar(255) NOT NULL,
  `email_usuario_remetente` varchar(255) NOT NULL,
  `id_usuario_destinatario` int NOT NULL,
  `id_usuario_remetente` int NOT NULL,
  `id_status_email` int NOT NULL,
  `texto_email` varchar(255) NOT NULL,
  `id_tipo_destinatario` int NOT NULL,
  `id_tipo_remetente` int NOT NULL,
  `titulo_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci