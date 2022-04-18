package com.rwarquitetura.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
	
	@Query("select u from Projeto u where u.clienteSecundario.arquiteto.id = :idArquiteto")
	public List<Projeto> findByIdArquiteto(@Param("idArquiteto") Integer idArquiteto);
}
