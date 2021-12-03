package com.jun.tournament.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String teamName;
	private long totalWins;
	private long totalMatches;

	public Team(String teamName, long teamMatches) {
		this.teamName = teamName;
		this.totalMatches = teamMatches;
	}
}
