package com.malkomich.football.data.batch.matches.domain;

import com.malkomich.football.data.batch.util.number.NumberUtils;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Match {
    private static final String RESULT_SEPARATOR = "-";
    private static final Integer RESULT_SPLITTED_SIZE = 2;

    private String date;
    private String localTeam;
    private String visitorTeam;
    private String result;
    private Integer localGoals;
    private Integer visitorGoals;
    private String endpoint;
    private Integer season;

    public Match(final JsonObject json) {
        this.date = json.getString("date");
        this.localTeam = json.getString("localTeam");
        this.visitorTeam = json.getString("visitorTeam");
        this.result = json.getString("result");
        this.endpoint = json.getString("endpoint");
        this.season = json.getInteger("season");

        this.localGoals = localGoals();
        this.visitorGoals = visitorGoals();
    }

    private Integer localGoals() {
        final String[] splittedResult = result.split(RESULT_SEPARATOR);
        return (splittedResult.length == RESULT_SPLITTED_SIZE) ? NumberUtils.parseInt(splittedResult[0]) : null;
    }

    private Integer visitorGoals() {
        final String[] splittedResult = result.split(RESULT_SEPARATOR);
        return (splittedResult.length == RESULT_SPLITTED_SIZE) ? NumberUtils.parseInt(splittedResult[1]) : null;
    }
}
