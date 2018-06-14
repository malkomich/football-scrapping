package com.malkomich.football.data.batch.repository.service;

import com.malkomich.football.data.batch.repository.domain.league.LeagueEntity;
import com.malkomich.football.data.batch.repository.domain.match.MatchEntity;
import com.malkomich.football.data.batch.repository.domain.rank.RanksEntity;
import com.malkomich.football.data.batch.repository.domain.team.TeamEntity;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

@ProxyGen
public interface FootballRepository {

    static FootballRepository createProxy(final Vertx vertx, final String address) {
        return new FootballRepositoryVertxEBProxy(vertx, address);
    }

    @Fluent
    FootballRepository saveLeagues(final List<LeagueEntity> ligues, final Handler<AsyncResult<Void>> handler);

    @Fluent
    FootballRepository saveRanks(final List<RanksEntity> ranks, final Handler<AsyncResult<Void>> handler);

    @Fluent
    FootballRepository saveMatches(final List<MatchEntity> matches, final Handler<AsyncResult<Void>> handler);

    @Fluent
    FootballRepository saveTeams(final List<TeamEntity> teams, final Handler<AsyncResult<Void>> handler);

    @ProxyIgnore
    void close();
}
