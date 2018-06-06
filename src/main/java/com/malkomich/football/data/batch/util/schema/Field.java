package com.malkomich.football.data.batch.util.schema;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Field {
    private String selector;
    @Builder.Default
    private Type type = Type.TEXT;

    public Field(final JsonObject json) {
        this.selector = json.getString("selector");
        this.type = Type.valueOf(json.getString("type"));
    }
}
