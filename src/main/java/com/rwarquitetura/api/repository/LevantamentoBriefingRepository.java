package com.rwarquitetura.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.LevantamentoBriefing;

@Repository
public interface LevantamentoBriefingRepository extends JpaRepository<LevantamentoBriefing, Integer> {
	
	@Query("select u from LevantamentoBriefing u where u.idProjeto = :idProjeto")
	public List<LevantamentoBriefing> findByIdProjeto(@Param("idProjeto") Integer idProjeto);
}
