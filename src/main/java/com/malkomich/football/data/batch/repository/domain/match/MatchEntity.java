package com.malkomich.football.data.batch.repository.domain.match;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DataObject
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {
    private String date;
    private String localTeam;
    private String visitorTeam;
    private String result;
    private Integer localGoals;
    private Integer visitorGoals;
    private String endpoint;

    public MatchEntity(final JsonObject json) {
        this.date = json.getString("date");
        this.localTeam = json.getString("localTeam");
        this.visitorTeam = json.getString("visitorTeam");
        this.result = json.getString("result");
        this.localGoals = json.getInteger("localGoals");
        this.visitorGoals = json.getInteger("visitorGoals");
        this.endpoint = json.getString("endpoint");
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
