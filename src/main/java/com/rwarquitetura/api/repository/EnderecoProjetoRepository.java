package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.EnderecoProjeto;

@Repository
public interface EnderecoProjetoRepository extends JpaRepository<EnderecoProjeto, Integer> {
	
}
