package com.malkomich.football.data.batch.ranks.domain;

import com.malkomich.football.data.batch.util.DataModel;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@DataObject
@Builder
@AllArgsConstructor
public class RanksResponse extends DataModel {
    private List<Rank> ranks;

    public RanksResponse(final JsonObject json) {
        this.ranks = json.getJsonArray("ranks", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Rank::new)
            .collect(Collectors.toList());
    }
}
