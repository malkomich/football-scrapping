package com.malkomich.football.data.batch.matchDetails.domain;

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
public class MatchDetailsResponse {
    private List<MatchStatistic> matchStats;

    public MatchDetailsResponse(final JsonObject json) {
        this.matchStats = json.getJsonArray("matchStats", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(MatchStatistic::new)
            .collect(Collectors.toList());
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
