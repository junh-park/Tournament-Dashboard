package com.jun.tournament.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jun.tournament.model.Team;
import com.jun.tournament.service.TeamMatchService;

@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TeamMatchService service;

	private Team team;
	
	@BeforeEach
	private void setUp() {
		team = new Team("Melbourne Stars", 50);
		team.setId(1);
		team.setTotalWins(20);
	}
	
	@Test
	public void returnTeamInformation_whenASpecificTeamNameProvided() throws Exception {
		when(service.getTeam("Melbourne Stars")).thenReturn(team);
		
		mvc.perform(get("/teams/{teamName}", "Melbourne Stars"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.teamName").value("Melbourne Stars"));
		verify(service).getTeam("Melbourne Stars");
	}


}
