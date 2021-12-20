package com.jun.tournament.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jun.tournament.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long>{
	List<Match> findByteam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable page);
	
	default List<Match> findLatestMatches(String teamName, int count) {
		Pageable pageRequest = PageRequest.of(0, count);
		return findByteam1OrTeam2OrderByDateDesc(teamName, teamName, pageRequest);
	}
	
	@Query("select m from Match m where (team1 = :teamName or team2 = :teamName) "
			+ "and date between :startDate and :endDate order by date desc")
	List<Match> findTeamMatchesByTeamNameBetween(
			@Param("teamName") String teamName, @Param("startDate") LocalDate start, @Param("endDate") LocalDate end);
	
}
