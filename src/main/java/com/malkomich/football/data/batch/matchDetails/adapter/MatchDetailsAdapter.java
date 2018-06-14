package com.malkomich.football.data.batch.matchDetails.adapter;

import com.google.common.collect.ImmutableMap;
import com.malkomich.football.data.batch.matchDetails.domain.MatchDetailsResponse;
import com.malkomich.football.data.batch.matchDetails.domain.MatchStatistic;
import com.malkomich.football.data.batch.matchDetails.domain.Statistic;
import com.malkomich.football.data.batch.matches.domain.Match;
import com.malkomich.football.data.batch.matches.schema.MatchesPageSchema;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.util.number.NumberUtils;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class MatchDetailsAdapter {

    private static final Map<String, Statistic> statsMatcher = ImmutableMap.<String, Statistic>builder()
        .put("Posesión del balón", Statistic.POSSESSION)
        .put("Goles", Statistic.GOALS)
        .put("Tiros a puerta", Statistic.SHOTS_IN)
        .put("Tiros fuera", Statistic.SHOTS_OUT)
        .put("Total tiros", Statistic.TOTAL_SHOTS)
        .put("Paradas del portero", Statistic.GOALKEEPER_STOPS)
        .put("Saques de esquina", Statistic.CORNERS)
        .put("Fueras de juego", Statistic.OFFSIDES)
        .put("Tarjetas amarillas", Statistic.YELLOW_CARDS)
        .put("Tarjetas rojas", Statistic.RED_CARDS)
        .put("Asistencias", Statistic.ASSISTS)
        .put("Lesiones", Statistic.INJURIES)
        .put("Sustituciones", Statistic.REPLACEMENTS)
        .put("Faltas", Statistic.FOULS)
        .build();

    public MatchDetailsResponse execute(final ScrappingResponse scrappingResponse) {
        return MatchDetailsResponse.builder()
            .matchStats(adaptMatchStatistics(scrappingResponse))
            .build();
    }

    private List<MatchStatistic> adaptMatchStatistics(final ScrappingResponse scrappingResponse) {
        return scrappingResponse.getEntryFields()
            .stream()
            .map(this::adaptStatistic)
            .collect(Collectors.toList());
    }

    private MatchStatistic adaptStatistic(final ScrappingFields scrappingFields) {
        val fields = scrappingFields.getFields();
        return MatchStatistic.builder()
            .statistic(statisticFromString(fields.get(MatchesPageSchema.VISITOR_TEAM_SELECTOR)))
            .localValue(NumberUtils.parseInt(fields.get(MatchesPageSchema.RESULT_SELECTOR)))
            .visitorValue(NumberUtils.parseInt(fields.get(MatchesPageSchema.VISITOR_TEAM_SELECTOR)))
            .build();
    }

    private Statistic statisticFromString(final String value) {
        if (value == null) {
            return null;
        }
        return statsMatcher.get(value);
    }
}
