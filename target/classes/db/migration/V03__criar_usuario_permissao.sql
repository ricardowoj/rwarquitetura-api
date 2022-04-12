CREATE TABLE `tab_usuario_permissao` (
  `id_usuario` int NOT NULL,
  `id_permissao` int NOT NULL,
  KEY `FKruw0un6jftj4pnabmmwkm5hy3` (`id_permissao`),
  KEY `FK4tj7jrw9inw3r7nxabmjc22vo` (`id_usuario`),
  CONSTRAINT `FK4tj7jrw9inw3r7nxabmjc22vo` FOREIGN KEY (`id_usuario`) REFERENCES `tab_usuario` (`id`),
  CONSTRAINT `FKruw0un6jftj4pnabmmwkm5hy3` FOREIGN KEY (`id_permissao`) REFERENCES `tab_permissao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci