package com.jun.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.tournament.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
	
	Team findByTeamName(String teamName);
}
