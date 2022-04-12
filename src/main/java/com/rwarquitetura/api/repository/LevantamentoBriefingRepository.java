package com.rwarquitetura.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rwarquitetura.api.model.LevantamentoBriefing;

@Repository
public interface LevantamentoBriefingRepository extends JpaRepository<LevantamentoBriefing, Integer> {
	
}
