package com.malkomich.football.data.batch.matches.service;

import com.malkomich.football.data.batch.matches.domain.MatchesRequest;
import com.malkomich.football.data.batch.matches.domain.MatchesResponse;
import com.malkomich.football.data.batch.util.ProxyService;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
public interface MatchService extends ProxyService<MatchesRequest, MatchesResponse> {
    static MatchService createProxy(final Vertx vertx, final String address) {
        return new MatchServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    MatchService execute(final MatchesRequest request, final Handler<AsyncResult<MatchesResponse>> handler);

    @ProxyIgnore
    void close();
}
