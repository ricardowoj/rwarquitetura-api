package com.rwarquitetura.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.ClienteSecundario;

@Repository
public interface ClienteSecundarioRepository extends JpaRepository<ClienteSecundario, Integer> {
	
	@Query("select u from ClienteSecundario u where u.numeroDoc = :numeroDoc")
	public ClienteSecundario findByNumeroDoc(@Param("numeroDoc") String numeroDoc);
	
	@Query("select u from ClienteSecundario u where u.usuario.id = :idUsuario")
	public ClienteSecundario findByIdUsuario(@Param("idUsuario") Integer idUsuario);
	
	@Query("select u from ClienteSecundario u where u.arquiteto.id = :idArquiteto")
	public List<ClienteSecundario> findByIdArquiteto(@Param("idArquiteto") Integer idArquiteto);
}
