package com.malkomich.football.data.batch.repository.domain.team;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagementEntity {
    private String manager;
    private String president;
    private Integer foundationYear;
    private Integer playersNumber;
    private Double annualBudget;

    public ManagementEntity(final JsonObject json) {
        this.manager = json.getString("manager");
        this.president = json.getString("president");
        this.foundationYear = json.getInteger("foundationYear");
        this.playersNumber = json.getInteger("playersNumber");
        this.annualBudget = json.getDouble("annualBudget");
    }
}
