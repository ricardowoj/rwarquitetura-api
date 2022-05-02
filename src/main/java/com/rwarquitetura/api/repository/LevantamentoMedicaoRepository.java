package com.rwarquitetura.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.LevantamentoMedicao;

@Repository
public interface LevantamentoMedicaoRepository extends JpaRepository<LevantamentoMedicao, Integer> {

	@Query("select u from LevantamentoMedicao u where u.idProjeto = :idProjeto")
	public List<LevantamentoMedicao> findByIdProjeto(@Param("idProjeto") Integer idProjeto);

}
