package com.malkomich.football.data.batch.util;

import io.vertx.core.json.JsonObject;

public abstract class DataModel {

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }
}
