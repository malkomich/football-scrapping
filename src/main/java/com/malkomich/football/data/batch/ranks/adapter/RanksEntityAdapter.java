package com.malkomich.football.data.batch.ranks.adapter;

import com.malkomich.football.data.batch.ranks.domain.Rank;
import com.malkomich.football.data.batch.ranks.domain.RanksResponse;
import com.malkomich.football.data.batch.repository.domain.rank.RanksEntity;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class RanksEntityAdapter {

    public List<RanksEntity> execute(final RanksResponse ranksResponse) {
        return ranksResponse.getRanks()
            .stream()
            .map(this::execute)
            .collect(Collectors.toList());
    }

    private RanksEntity execute(final Rank rank) {
        return RanksEntity.builder()
            .team(rank.getTeam())
            .position(rank.getPosition())
            .score(rank.getScore())
            .wonMatches(rank.getWonMatches())
            .drawMatches(rank.getDrawMatches())
            .lostMatches(rank.getLostMatches())
            .goalsInFavour(rank.getGoalsInFavour())
            .goalsAgainst(rank.getGoalsAgainst())
            .build();
    }
}
