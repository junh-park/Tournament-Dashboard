package com.jun.tournament.batch.data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.jun.tournament.model.Match;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchData, Match>{

	public Match process(MatchData matchData) throws Exception {
		Match match = new Match();
		match.setId(Long.parseLong(matchData.getId()));
		match.setCity(matchData.getCity());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat reformatter = new SimpleDateFormat("dd/MM/yyyy");
		Date formattedDate = formatter.parse(matchData.getDate());
		match.setDate(LocalDate.parse(reformatter.format(formattedDate)));
		
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
		match.setResult(matchData.getResult());
		match.setResultMargin(matchData.getResult_margin());
		match.setUmpire1(matchData.getUmpire1());
		match.setUmpire2(matchData.getUmpire2());
		log.info("Match " + match.getId() + " is being processed");
		return match;
	}
	
}
