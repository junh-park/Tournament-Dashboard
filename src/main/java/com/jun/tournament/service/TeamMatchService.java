package com.jun.tournament.service;

import org.springframework.stereotype.Service;

import com.jun.tournament.model.Team;
import com.jun.tournament.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMatchService {
	private final TeamRepository repo;
	
	public Team getTeam(String teamName) {
		return repo.findByTeamName(teamName);
	}

}
