package com.malkomich.football.data.batch.scrapping.domain;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ScrappingFields {
    private Map<String, String> fields;

    public ScrappingFields(final JsonObject json) {
        this.fields = Optional.ofNullable(json.getJsonObject("fields"))
            .map(JsonObject::getMap)
            .map(Map::entrySet)
            .orElse(Collections.emptySet())
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));
    }
}
