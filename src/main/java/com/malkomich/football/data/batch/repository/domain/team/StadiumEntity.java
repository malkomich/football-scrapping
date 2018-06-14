package com.malkomich.football.data.batch.repository.domain.team;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StadiumEntity {
    private String stadiumName;
    private Integer stadiumCapacity;
    private Integer stadiumBuild;

    public StadiumEntity(final JsonObject json) {
        this.stadiumName = json.getString("stadiumName");
        this.stadiumCapacity = json.getInteger("stadiumCapacity");
        this.stadiumBuild = json.getInteger("stadiumBuild");
    }
}
