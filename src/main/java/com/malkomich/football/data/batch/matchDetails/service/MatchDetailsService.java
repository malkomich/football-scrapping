package com.malkomich.football.data.batch.matchDetails.service;

import com.malkomich.football.data.batch.matchDetails.domain.MatchDetailsRequest;
import com.malkomich.football.data.batch.matchDetails.domain.MatchDetailsResponse;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
public interface MatchDetailsService {
    static MatchDetailsService createProxy(final Vertx vertx, final String address) {
        return new MatchDetailsServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    MatchDetailsService execute(final MatchDetailsRequest request,
                                final Handler<AsyncResult<MatchDetailsResponse>> handler);

    @ProxyIgnore
    void close();
}
