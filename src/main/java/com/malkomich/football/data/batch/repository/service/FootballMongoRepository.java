package com.malkomich.football.data.batch.repository.service;

import com.malkomich.football.data.batch.repository.domain.league.LeagueEntity;
import com.malkomich.football.data.batch.repository.domain.match.MatchEntity;
import com.malkomich.football.data.batch.repository.domain.rank.RanksEntity;
import com.malkomich.football.data.batch.repository.domain.team.TeamEntity;
import com.malkomich.football.data.batch.rest.Headers;
import com.malkomich.football.data.batch.rest.domain.WebServiceException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

@Slf4j
@Builder
public class FootballMongoRepository implements FootballRepository {
    private static final String URL = "REPOSITORY_SERVICE_HOST";
    private static final String PORT = "REPOSITORY_SERVICE_PORT";
    private static final Integer DEFAULT_PORT = 80;
    private static final String SSL = "REPOSITORY_SERVICE_SSL";
    private static final Boolean DEFAULT_SSL = true;
    private static final String ENDPOINT_LEAGUE = "REPOSITORY_SERVICE_ENDPOINT_LEAGUE";
    private static final String ENDPOINT_RANK = "REPOSITORY_SERVICE_ENDPOINT_RANK";
    private static final String ENDPOINT_MATCH = "REPOSITORY_SERVICE_ENDPOINT_MATCH";
    private static final String ENDPOINT_TEAM = "REPOSITORY_SERVICE_ENDPOINT_TEAM";

    private WebClient client;
    private JsonObject config;

    @Override
    public FootballRepository saveLeagues(final List<LeagueEntity> ligues, final Handler<AsyncResult<Void>> handler) {
        final String endpoint = config.getString(ENDPOINT_LEAGUE);
        execute(endpoint, ligues, handler);
        return this;
    }

    @Override
    public FootballRepository saveRanks(List<RanksEntity> ranks, Handler<AsyncResult<Void>> handler) {
        final String endpoint = config.getString(ENDPOINT_RANK);
        execute(endpoint, ranks, handler);
        return this;
    }

    @Override
    public FootballRepository saveMatches(final List<MatchEntity> matches, final Handler<AsyncResult<Void>> handler) {
        final String endpoint = config.getString(ENDPOINT_MATCH);
        execute(endpoint, matches, handler);
        return this;
    }

    @Override
    public FootballRepository saveTeams(final List<TeamEntity> teams, final Handler<AsyncResult<Void>> handler) {
        final String endpoint = config.getString(ENDPOINT_TEAM);
        execute(endpoint, teams, handler);
        return this;
    }

    @Override
    public void close() {
        client.close();
    }

    private void execute(final String endpoint, final Object data, final Handler<AsyncResult<Void>> handler) {
        client.post(config.getInteger(PORT, DEFAULT_PORT), config.getString(URL), endpoint)
            .ssl(config.getBoolean(SSL, DEFAULT_SSL))
            .putHeader(Headers.ACCEPT_HEADER, Headers.FORMAT)
            .putHeader(Headers.CONTENT_HEADER, Headers.FORMAT)
            .sendJson(data, asyncResult -> handleResult(asyncResult, handler));
    }

    private void handleResult(final AsyncResult<HttpResponse<Buffer>> asyncResult,
                              final Handler<AsyncResult<Void>> resultHandler) {
        if (asyncResult.failed()) {
            val error = new WebServiceException(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
                asyncResult.cause().getMessage());
            log.error("Bulk operations failed");
            resultHandler.handle(Future.failedFuture(error));
            return;
        }
        log.info("Bulk operations success");
        resultHandler.handle(Future.succeededFuture());
    }
}
