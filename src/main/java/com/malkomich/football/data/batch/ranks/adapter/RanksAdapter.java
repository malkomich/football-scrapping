package com.malkomich.football.data.batch.ranks.adapter;

import com.malkomich.football.data.batch.ranks.domain.Rank;
import com.malkomich.football.data.batch.ranks.domain.RanksResponse;
import com.malkomich.football.data.batch.ranks.schema.RankPageSchema;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.util.number.NumberUtils;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class RanksAdapter {

    public RanksResponse execute(final ScrappingResponse scrappingResponse) {
        return RanksResponse.builder()
            .ranks(adaptRanks(scrappingResponse))
            .build();
    }

    private List<Rank> adaptRanks(final ScrappingResponse scrappingResponse) {
        return scrappingResponse.getEntryFields()
            .stream()
            .map(this::adaptRank)
            .collect(Collectors.toList());
    }

    private Rank adaptRank(final ScrappingFields scrappingFields) {
        val fields = scrappingFields.getFields();
        return Rank.builder()
            .team(fields.get(RankPageSchema.TEAM_SELECTOR))
            .position(NumberUtils.parseInt(fields.get(RankPageSchema.POSITION_SELECTOR)))
            .score(NumberUtils.parseInt(fields.get(RankPageSchema.SCORE_SELECTOR)))
            .playedMatches(NumberUtils.parseInt(fields.get(RankPageSchema.PLAYED_MATCHES_SELECTOR)))
            .wonMatches(NumberUtils.parseInt(fields.get(RankPageSchema.WON_MATCHES_SELECTOR)))
            .drawMatches(NumberUtils.parseInt(fields.get(RankPageSchema.DRAW_MATCHES_SELECTOR)))
            .lostMatches(NumberUtils.parseInt(fields.get(RankPageSchema.LOST_MATCHES_SELECTOR)))
            .goalsInFavour(NumberUtils.parseInt(fields.get(RankPageSchema.GOALS_IN_FAVOUR_SELECTOR)))
            .goalsAgainst(NumberUtils.parseInt(fields.get(RankPageSchema.GOALS_AGAINST_SELECTOR)))
            .build();
    }
}
