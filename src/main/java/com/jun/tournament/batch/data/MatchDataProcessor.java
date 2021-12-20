 package com.jun.tournament.batch.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import com.jun.tournament.model.Match;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchData, Match>{

	public Match process(MatchData matchData) throws Exception {
		log.info("Match " + matchData.getId() + " is being processed");
		return matchMaper(matchData);
	}

	private Match matchMaper(MatchData matchData) {
		Match match = new Match();
		
		match.setId(Long.parseLong(matchData.getId()));
		match.setCity(matchData.getCity());
		
		DateTimeFormatter reformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		match.setDate(LocalDate.parse(matchData.getDate(), reformatter));

		match.setPlayerOfMatch(matchData.getPlayer_of_match());
		match.setVenue(matchData.getVenue());
		String firstInningsTeam, secondInningsTeam;
		
		if ("bat".equals(matchData.getToss_decision())) {			
			firstInningsTeam = matchData.getToss_winner();
			secondInningsTeam = matchData.getToss_winner().equals(matchData.getTeam1()) 
					? matchData.getTeam2() : matchData.getTeam1();
		} else {
			secondInningsTeam = matchData.getToss_winner();
			firstInningsTeam = matchData.getToss_winner().equals(matchData.getTeam1()) 
					? matchData.getTeam2() : matchData.getTeam1();
		}
		
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		match.setTossWinner(matchData.getToss_winner());
		match.setTossDecision(matchData.getToss_decision());
		match.setMatchWinner(matchData.getWinner());
		match.setResult(matchData.getResult());
		match.setResultMargin(matchData.getResult_margin());
		match.setUmpire1(matchData.getUmpire1());
		match.setUmpire2(matchData.getUmpire2());
		return match;
	}
	
}
