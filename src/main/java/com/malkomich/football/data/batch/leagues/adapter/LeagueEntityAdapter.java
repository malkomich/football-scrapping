package com.malkomich.football.data.batch.leagues.adapter;

import com.malkomich.football.data.batch.leagues.domain.League;
import com.malkomich.football.data.batch.leagues.domain.LeaguesResponse;
import com.malkomich.football.data.batch.repository.domain.league.LeagueEntity;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class LeagueEntityAdapter {

    public List<LeagueEntity> execute(final LeaguesResponse leaguesResponse) {
        return leaguesResponse.getLeagues()
            .stream()
            .map(this::execute)
            .collect(Collectors.toList());
    }

    private LeagueEntity execute(final League league) {
        return LeagueEntity.builder()
            .name(league.getName())
            .url(league.getUrl())
            .build();
    }
}
