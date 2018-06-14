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
public class LeaguesRequest extends DataModel {
    private List<String> leaguesEndpoints;

    public LeaguesRequest(final JsonObject json) {
        this.leaguesEndpoints = json.getJsonArray("leaguesEndpoints", new JsonArray())
            .stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    }
}
