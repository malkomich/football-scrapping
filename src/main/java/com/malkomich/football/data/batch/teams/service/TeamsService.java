package com.malkomich.football.data.batch.teams.service;

import com.malkomich.football.data.batch.teams.domain.TeamsRequest;
import com.malkomich.football.data.batch.teams.domain.TeamsResponse;
import com.malkomich.football.data.batch.util.ProxyService;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.Vertx;

@ProxyGen
public interface TeamsService extends ProxyService<TeamsRequest, TeamsResponse> {
    static TeamsService createProxy(final Vertx vertx, final String address) {
        return new TeamsServiceVertxEBProxy(vertx, address);
    }

    @ProxyIgnore
    void close();
}
