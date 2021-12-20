package com.jun.tournament.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jun.tournament.model.Match;
import com.jun.tournament.model.Team;
import com.jun.tournament.service.TeamMatchService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
	private final TeamMatchService service;
	
	@GetMapping
	public ResponseEntity<List<Team>> getAllTeams() {
		return new ResponseEntity(service.getAllTeam(), HttpStatus.OK);
	}

	@GetMapping("/{teamName}")
	public ResponseEntity<Team> getTeamInfo(@PathVariable String teamName) {
		return new ResponseEntity<>(service.getTeamInformation(teamName), HttpStatus.OK);
	}
	
	@GetMapping("/{teamName}/matches")
	public ResponseEntity<List<Match>> getTeamMatchesByYear(@PathVariable String teamName, @RequestParam int year) {
		return new ResponseEntity(service.getTeamMatchesByYear(teamName, year), HttpStatus.OK);
	}

}
