package com.malkomich.football.data.batch.scrapping.service;

import com.malkomich.football.data.batch.scrapping.domain.ScrappingRequest;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
public interface ScrappingService {

    static ScrappingService createProxy(final Vertx vertx, final String address) {
        return new ScrappingServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    ScrappingService execute(final ScrappingRequest request, final Handler<AsyncResult<ScrappingResponse>> handler);

    @ProxyIgnore
    void close();
}
