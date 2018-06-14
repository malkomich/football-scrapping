package com.malkomich.football.data.batch.leagues.domain;

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
public class LeaguesResponse extends DataModel {
    private List<League> leagues;

    public LeaguesResponse(final JsonObject json) {
        this.leagues = json.getJsonArray("leagues", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(League::new)
            .collect(Collectors.toList());
    }
}
