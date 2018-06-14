package com.malkomich.football.data.batch.matches.adapter;

import com.malkomich.football.data.batch.matches.domain.Match;
import com.malkomich.football.data.batch.matches.domain.MatchesResponse;
import com.malkomich.football.data.batch.matches.schema.MatchesPageSchema;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class MatchAdapter {

    public MatchesResponse execute(final ScrappingResponse scrappingResponse, final Integer season) {
        return MatchesResponse.builder()
            .matches(adaptMatches(scrappingResponse, season))
            .build();
    }

    private List<Match> adaptMatches(final ScrappingResponse scrappingResponse, final Integer season) {
        return scrappingResponse.getEntryFields()
            .stream()
            .map(scrappingFields ->  adaptMatch(scrappingFields, season))
            .collect(Collectors.toList());
    }

    private Match adaptMatch(final ScrappingFields scrappingFields, final Integer season) {
        val fields = scrappingFields.getFields();
        return Match.builder()
            .date(fields.get(MatchesPageSchema.DATE_SELECTOR))
            .localTeam(fields.get(MatchesPageSchema.LOCAL_TEAM_SELECTOR))
            .visitorTeam(fields.get(MatchesPageSchema.VISITOR_TEAM_SELECTOR))
            .result(fields.get(MatchesPageSchema.RESULT_SELECTOR))
            .endpoint(fields.get(MatchesPageSchema.MATCH_ENDPOINT_SELECTOR))
            .season(season)
            .build();
    }
}
