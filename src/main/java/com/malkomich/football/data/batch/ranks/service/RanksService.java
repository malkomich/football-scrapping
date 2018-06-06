package com.malkomich.football.data.batch.ranks.service;

import com.malkomich.football.data.batch.ranks.domain.RanksRequest;
import com.malkomich.football.data.batch.ranks.domain.RanksResponse;
import com.malkomich.football.data.batch.util.ProxyService;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.Vertx;

@ProxyGen
public interface RanksService extends ProxyService<RanksRequest, RanksResponse> {
    static RanksService createProxy(final Vertx vertx, final String address) {
        return new RanksServiceVertxEBProxy(vertx, address);
    }

    @ProxyIgnore
    void close();
}
