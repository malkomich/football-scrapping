package com.malkomich.football.data.batch.repository.domain.rank;

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
public class RanksEntity {
    private String team;
    private Integer position;
    private Integer score;
    private Integer playedMatches;
    private Integer wonMatches;
    private Integer drawMatches;
    private Integer lostMatches;
    private Integer goalsInFavour;
    private Integer goalsAgainst;

    public RanksEntity(final JsonObject json) {
        this.team = json.getString("team");
        this.position = json.getInteger("position");
        this.score = json.getInteger("score");
        this.playedMatches = json.getInteger("playedMatches");
        this.wonMatches = json.getInteger("wonMatches");
        this.drawMatches = json.getInteger("drawMatches");
        this.lostMatches = json.getInteger("lostMatches");
        this.goalsInFavour = json.getInteger("goalsInFavour");
        this.goalsAgainst = json.getInteger("goalsAgainst");
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
