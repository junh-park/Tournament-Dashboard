package com.jun.tournament.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.jun.tournament.model.Match;
import com.jun.tournament.model.Team;
import com.jun.tournament.repository.MatchRepository;
import com.jun.tournament.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamMatchService {
	private static final int NUMBER_OF_MATCHES_TO_DISPLAY = 5;
	private final TeamRepository teamRepo;
	private final MatchRepository matchRepo;
	
	public Team getTeamInformation(String teamName) {
		Team team = teamRepo.findByTeamName(teamName);
		team.setMatches(matchRepo.findLatestMatches(teamName, NUMBER_OF_MATCHES_TO_DISPLAY));
		return team;
	}

	public List<Match> getTeamMatchesByYear(String teamName, int year) {
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);
		return matchRepo.findTeamMatchesByTeamNameBetween(teamName, start, end);
	}

	public List<Team> getAllTeam() {
		return teamRepo.findAll();
	}

}
