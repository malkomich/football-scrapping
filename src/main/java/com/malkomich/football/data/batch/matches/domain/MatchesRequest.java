package com.malkomich.football.data.batch.matches.domain;

import com.malkomich.football.data.batch.util.DataModel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@DataObject
@Builder
@AllArgsConstructor
public class MatchesRequest extends DataModel {
    private String league;
    private Integer season;
    private Integer group;

    public MatchesRequest(final JsonObject json) {
        this.league = json.getString("league");
        this.season = json.getInteger("season");
        this.group = json.getInteger("group");
    }
}
