package com.jun.tournament.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jun.tournament.model.Team;
import com.jun.tournament.service.TeamMatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TeamController {
	private final TeamMatchService service;

	@GetMapping("/teams/{teamName}")
	public Team getTeamInformation(@PathVariable String teamName) {
		return service.getTeam(teamName);
	}

}
