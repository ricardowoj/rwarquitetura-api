package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.ParcelaPagamento;

@Repository
public interface ParcelaPagamentoRepository extends JpaRepository<ParcelaPagamento, Integer> {
	
}
