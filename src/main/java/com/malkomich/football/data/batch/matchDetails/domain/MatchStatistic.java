package com.malkomich.football.data.batch.matchDetails.domain;

import com.malkomich.football.data.batch.util.number.NumberUtils;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MatchStatistic {

    private Statistic statistic;
    private Integer localValue;
    private Integer visitorValue;

    public MatchStatistic(final JsonObject json) {
        this.statistic = adaptStatistic(json.getString("statistic"));
        this.localValue = NumberUtils.parseInt("localValue");
        this.visitorValue = NumberUtils.parseInt("visitorValue");
    }

    private Statistic adaptStatistic(final String statistic) {
        if (statistic == null) {
            return null;
        }
        return Statistic.valueOf(statistic);
    }
}
