package com.malkomich.football.data.batch.scrapping.domain;

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
public class ScrappingResponse {
    private List<ScrappingFields> entryFields;

    public ScrappingResponse(final JsonObject json) {
        this.entryFields = json.getJsonArray("entryFields", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(ScrappingFields::new)
            .collect(Collectors.toList());
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
