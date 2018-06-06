package com.malkomich.football.data.batch.scrapping.domain;

import com.malkomich.football.data.batch.util.schema.Field;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@DataObject
@Builder
@AllArgsConstructor
public class ScrappingRequest {

    private String url;
    private String containerSelector;
    private Collection<Field> fieldSelectors;

    public ScrappingRequest(final JsonObject json) {
        this.url = json.getString("url");
        this.containerSelector = json.getString("containerSelector");
        this.fieldSelectors = getFields(json);
    }

    private Collection<Field> getFields(final JsonObject json) {
        return json.getJsonArray("fields", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Field::new)
            .collect(Collectors.toSet());
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
