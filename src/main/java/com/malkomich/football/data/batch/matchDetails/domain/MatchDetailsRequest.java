package com.malkomich.football.data.batch.matchDetails.domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@DataObject
@Builder
@AllArgsConstructor
public class MatchDetailsRequest {
    private String localTeam;
    private String visitorTeam;
    private Integer season;

    public MatchDetailsRequest(final JsonObject json) {
        this.localTeam = json.getString("localTeam");
        this.visitorTeam = json.getString("visitorTeam");
        this.season = json.getInteger("season");
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
