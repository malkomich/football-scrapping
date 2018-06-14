package com.malkomich.football.data.batch.batch;

import com.malkomich.football.data.batch.leagues.domain.League;
import com.malkomich.football.data.batch.leagues.domain.LeaguesRequest;
import com.malkomich.football.data.batch.leagues.domain.LeaguesResponse;
import com.malkomich.football.data.batch.leagues.service.LeaguesService;
import com.malkomich.football.data.batch.matches.domain.MatchesRequest;
import com.malkomich.football.data.batch.matches.service.MatchService;
import com.malkomich.football.data.batch.ranks.domain.RanksRequest;
import com.malkomich.football.data.batch.ranks.domain.RanksResponse;
import com.malkomich.football.data.batch.ranks.service.RanksService;
import com.malkomich.football.data.batch.teams.domain.TeamsRequest;
import com.malkomich.football.data.batch.teams.domain.TeamsResponse;
import com.malkomich.football.data.batch.teams.service.TeamsService;
import com.malkomich.football.data.batch.util.DataModel;
import com.malkomich.football.data.batch.util.ProxyService;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@AllArgsConstructor
public class BatchExecutor {

    private static final Integer DEFAULT_SEASON = 2018; // TODO: Adapt to scrap specific season (or since that season)
    private static final Integer DEFAULT_GROUP = 1; // TODO: Adapt to work with tournaments

    private static final String SECTION_POPULARS = "";
    private static final String SECTION_SPAIN = "futbol_espana";
    private static final String SECTION_OTHERS = "otras";
    private static final String SECTION_TOURNAMENTS = "competiciones";

    private Vertx vertx;
    private LeaguesService leaguesService;
    private RanksService ranksService;
    private MatchService matchService;
    private TeamsService teamsService;

    public void execute() {
        log.info("BATCH STARTED!");
        executeLeaguesService();
    }

    private void executeLeaguesService() {
        val request = LeaguesRequest.builder()
            .leaguesEndpoints(newArrayList(SECTION_SPAIN, SECTION_OTHERS))
            .build();
        leaguesService.execute(request, asyncResult -> {
            if (asyncResult.failed()) {
                failedReply(asyncResult);
                return;
            }
            CompositeFuture.join(Stream.of(executeRanksService(asyncResult.result()).stream(),
                                           executeMatchesService(asyncResult.result()),
                                           executeTeamsService(asyncResult.result()))
                .filter(future -> future instanceof Future)
                .map(Future.class::cast)
                .collect(Collectors.toList()))
                .setHandler(this::finish);
        });
    }

    private Collection<Future<RanksResponse>> executeRanksService(final LeaguesResponse leaguesResponse) {
        return leaguesResponse.getLeagues().stream()
            .map(league ->
                RanksRequest.builder()
                    .league(league.getEndpoint())
                    .season(DEFAULT_SEASON)
                    .group(DEFAULT_GROUP)
                    .build())
            .map(request -> {
                final Future<RanksResponse> future = Future.future();
                ranksService.execute(request, future.completer());
                return future;
            })
            .collect(Collectors.toList());
    }

    private Collection<Future<DataModel>> executeMatchesService(final LeaguesResponse leaguesResponse) {
        return generateFutures(leaguesResponse, matchService, league ->
            MatchesRequest.builder()
                .league(league.getEndpoint())
                .season(DEFAULT_SEASON)
                .group(DEFAULT_GROUP)
                .build());

//        return leaguesResponse.getLeagues().stream()
//            .map(league ->
//                MatchesRequest.builder()
//                    .league(league.getEndpoint())
//                    .season(DEFAULT_SEASON)
//                    .group(DEFAULT_GROUP)
//                    .build())
//            .map(request -> {
//                final Future<MatchesResponse> future = Future.future();
//                matchService.execute(request, future.completer());
//                return future;
//            })
//            .collect(Collectors.toList());
    }

    private Collection<Future<TeamsResponse>> executeTeamsService(final LeaguesResponse leaguesResponse) {
        return leaguesResponse.getLeagues().stream()
            .map(league ->
                TeamsRequest.builder()
                    .league(league.getEndpoint())
                    .season(DEFAULT_SEASON)
                    .group(DEFAULT_GROUP)
                    .build())
            .map(request -> {
                final Future<TeamsResponse> future = Future.future();
                teamsService.execute(request, future.completer());
                return future;
            })
            .collect(Collectors.toList());
    }

    private Collection<Future<DataModel>> generateFutures(final LeaguesResponse leaguesResponse,
                                                          final ProxyService proxyService,
                                                          final Function<? super League, DataModel> requestMapper) {
        return leaguesResponse.getLeagues().stream()
            .map(requestMapper)
            .map(request -> {
                final Future<DataModel> future = Future.future();
                proxyService.execute(request, future.completer());
                return future;
            })
            .collect(Collectors.toList());
    }

    private void finish(final AsyncResult<CompositeFuture> asyncResult) {
        if (asyncResult.failed()) {
            failedReply(asyncResult);
            return;
        }
        log.info("BATCH SUCCESSFULLY FINISHED");
        vertx.close();
    }

    private void failedReply(final AsyncResult response) {
        val responseMessage = response.cause().getMessage();
        log.error("BATCH FAILED WITH ERROR: {}", responseMessage);
        vertx.close();
    }
}
