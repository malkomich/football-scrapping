package com.malkomich.football.data.batch.teams.domain;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Team {
    private String name;
    private String fullName;
    private String shortName;
    private Management management;
    private Stadium stadium;
    private Contact contact;

    public Team(final JsonObject json) {
        this.name = json.getString("name");
        this.fullName = json.getString("fullName");
        this.shortName = json.getString("shortName");
        this.management = new Management(json);
        this.stadium = new Stadium(json);
        this.contact = new Contact(json);
    }
}
