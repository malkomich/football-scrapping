package com.malkomich.football.data.batch.leagues.domain;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class League {
    private String name;
    private String url;
    private String endpoint;
    private Integer groups;
    // TODO: Something to know valid season years (e.g. World Championship)
    // TODO: Check if is possible and/or necessary to get other fields: Country, init & end dates, number of teams.

    public League(final JsonObject json) {
        this.name = json.getString("name");
        this.url = json.getString("url");
        this.endpoint = json.getString("endpoint");
        this.groups = json.getInteger("groups");
    }
}
