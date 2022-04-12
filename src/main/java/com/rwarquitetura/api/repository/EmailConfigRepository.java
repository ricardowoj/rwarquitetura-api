package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.EmailConfig;

@Repository
public interface EmailConfigRepository extends JpaRepository<EmailConfig, Integer> {
	
	@Query("select u from EmailConfig u where u.idUsuario = :idUsuario")
	public EmailConfig findByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
