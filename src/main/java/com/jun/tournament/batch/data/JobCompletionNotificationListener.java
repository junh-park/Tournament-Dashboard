package com.jun.tournament.batch.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.jun.tournament.model.Team;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	private final JdbcTemplate jdbcTemplate;
	private final EntityManager em;

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Job finished");

			Map<String, Team> teamData = new HashMap<>();

			em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
				.getResultList()
				.stream()
				.map(row -> new Team((String) row[0], (long) row[1]))
				.forEach(team -> teamData.put(team.getTeamName(), team));
			
			em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
				.getResultList()
				.stream()
				.forEach(row -> {
					Team team = teamData.get((String) row[0]);
					team.setTotalMatches(team.getTotalMatches() + (long) row[1]);
				});
			
			em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
				.getResultList()
				.stream()
				.forEach(row -> {
					Team team = teamData.get((String) row[0]);
					if (team != null) team.setTotalWins(team.getTotalWins() + (long) row[1]);
				});
			
			teamData.values().forEach(team -> em.persist(team));
			teamData.values().forEach(team -> System.out.println(team));
		}
	}

}
