package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
}
