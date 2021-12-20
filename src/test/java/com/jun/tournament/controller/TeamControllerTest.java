package com.jun.tournament.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import javax.xml.stream.events.Characters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.tournament.model.Match;
import com.jun.tournament.model.Team;
import com.jun.tournament.service.TeamMatchService;

@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TeamMatchService service;

	private Team team;

	private List<Match> matches;
	
	@BeforeEach
	private void setUp() {
		team = new Team("Melbourne Stars", 50);
		team.setId(1);
		team.setTotalWins(20);
		matches = List.of(new Match(), new Match(),new Match(), new Match(), new Match());
		matches.forEach(m -> m.setDate(LocalDate.of(2021, 12, 12)));
		team.setMatches(matches);
	}
	
	@Test
	public void returnTeamInformation_whenASpecificTeamNameProvided() throws Exception {
		when(service.getTeamInformation("Melbourne Stars")).thenReturn(team);
		
		mvc.perform(get("/teams/{teamName}", "Melbourne Stars"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.teamName").value("Melbourne Stars"))
			.andExpect(jsonPath("$.matches", hasSize(5)));
		verify(service).getTeamInformation("Melbourne Stars");
	}
	
	@Test
	public void getMatches_whenYearIsProvided() throws Exception {
		when(service.getTeamMatchesByYear("Melbourne Stars", 2021)).thenReturn(matches);
		
		mvc.perform(get("/teams/{teamName}/matches", "Melbourne Stars")
				.param("year", "2021"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(5)))
			.andExpect(jsonPath("$[0].date", is("2021-12-12")));
		verify(service).getTeamMatchesByYear("Melbourne Stars", 2021);
	}

	public static String mapJsonToString(final Object team) {
		try {
			return new ObjectMapper().writeValueAsString(team);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
