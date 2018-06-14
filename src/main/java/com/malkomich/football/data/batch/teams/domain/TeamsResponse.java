package com.malkomich.football.data.batch.teams.domain;

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
public class TeamsResponse extends DataModel {
    private List<Team> teams;

    public TeamsResponse(final JsonObject json) {
        this.teams = json.getJsonArray("teams", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Team::new)
            .collect(Collectors.toList());
    }
}
