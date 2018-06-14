package com.malkomich.football.data.batch.util;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface ProxyService<I extends DataModel, O extends DataModel> {

    @Fluent
    ProxyService execute(final I request, final Handler<AsyncResult<O>> handler);
}
