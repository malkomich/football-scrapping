package com.malkomich.football.data.batch.leagues.adapter;

import com.malkomich.football.data.batch.leagues.domain.League;
import com.malkomich.football.data.batch.leagues.domain.LeaguesResponse;
import com.malkomich.football.data.batch.leagues.schema.LeaguesPageSchema;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import io.vertx.core.CompositeFuture;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class LeagueAdapter {
    private static final String URL_SEPARATOR = "/";

    public LeaguesResponse execute(final CompositeFuture compositeFuture) {
        return LeaguesResponse.builder()
            .leagues(adaptLeagues(compositeFuture))
            .build();
    }

    private List<League> adaptLeagues(final CompositeFuture compositeFuture) {
        return compositeFuture.list().stream()
            .map(ScrappingResponse.class::cast)
            .map(ScrappingResponse::getEntryFields)
            .flatMap(List::stream)
            .map(this::adaptLeague)
            .collect(Collectors.toList());
    }

    private League adaptLeague(final ScrappingFields scrappingFields) {
        val fields = scrappingFields.getFields();
        return League.builder()
            .name(fields.get(LeaguesPageSchema.NAME_SELECTOR))
            .url(fields.get(LeaguesPageSchema.URL_SELECTOR))
            .endpoint(adaptEndpoint(fields.get(LeaguesPageSchema.URL_SELECTOR)))
            .build();
    }

    private String adaptEndpoint(final String completeURL) {
        final String endpoint =  completeURL.replace(LeaguesPageSchema.BASE_URL, "");
        final String lastCharacter = completeURL.substring(completeURL.length() - 1);
        if (URL_SEPARATOR.equals(lastCharacter)) {
            return endpoint.substring(0, endpoint.length() - 1);
        }
        return endpoint;
    }
}
