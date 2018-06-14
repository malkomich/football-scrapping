package com.malkomich.football.data.batch.matches.adapter;

import com.malkomich.football.data.batch.matches.domain.Match;
import com.malkomich.football.data.batch.matches.domain.MatchesResponse;
import com.malkomich.football.data.batch.repository.domain.match.MatchEntity;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MatchEntityAdapter {

    public List<MatchEntity> execute(final MatchesResponse matchesResponse) {
        return matchesResponse.getMatches()
            .stream()
            .map(this::execute)
            .collect(Collectors.toList());
    }

    private MatchEntity execute(final Match match) {
        return MatchEntity.builder()
            .localTeam(match.getLocalTeam())
            .visitorTeam(match.getVisitorTeam())
            .localGoals(match.getLocalGoals())
            .visitorGoals(match.getVisitorGoals())
            .date(match.getDate())
            .endpoint(match.getEndpoint())
            .build();
    }
}
