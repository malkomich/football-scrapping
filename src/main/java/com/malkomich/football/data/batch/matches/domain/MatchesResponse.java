package com.malkomich.football.data.batch.matches.domain;

import com.malkomich.football.data.batch.util.DataModel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@DataObject
@Builder
@AllArgsConstructor
public class MatchesResponse extends DataModel {
    private List<Match> matches;

    public MatchesResponse(final JsonObject json) {
        this.matches = json.getJsonArray("matches", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Match::new)
            .collect(Collectors.toList());
    }
}
