package com.malkomich.football.data.batch.leagues.service;

import com.malkomich.football.data.batch.leagues.domain.LeaguesRequest;
import com.malkomich.football.data.batch.leagues.domain.LeaguesResponse;
import com.malkomich.football.data.batch.util.ProxyService;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.Vertx;

@ProxyGen
public interface LeaguesService extends ProxyService<LeaguesRequest, LeaguesResponse> {
    static LeaguesService createProxy(final Vertx vertx, final String address) {
        return new LeaguesServiceVertxEBProxy(vertx, address);
    }

    @ProxyIgnore
    void close();
}
