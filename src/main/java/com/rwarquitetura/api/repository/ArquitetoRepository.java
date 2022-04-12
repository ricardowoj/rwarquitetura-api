package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.Arquiteto;

@Repository
public interface ArquitetoRepository extends JpaRepository<Arquiteto, Integer> {
	
	@Query("select u from Arquiteto u where u.usuario.email = :email")
	public Arquiteto findByEmail(@Param("email") String email);
}
