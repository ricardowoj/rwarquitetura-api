package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {

	@Query("select p from Permissao p where p.descricao = :descricao")
	public Permissao findByDescricao(@Param("descricao") String descricao);
}
