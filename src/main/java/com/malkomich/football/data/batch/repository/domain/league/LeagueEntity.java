package com.malkomich.football.data.batch.repository.domain.league;

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
public class LeagueEntity {
    private String name;
    private String url;

    public LeagueEntity(final JsonObject json) {
        this.name = json.getString("name");
        this.url = json.getString("url");
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
