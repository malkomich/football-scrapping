package com.malkomich.football.data.batch.leagues.service;

import com.malkomich.football.data.batch.leagues.adapter.LeagueAdapter;
import com.malkomich.football.data.batch.leagues.adapter.LeagueEntityAdapter;
import com.malkomich.football.data.batch.leagues.domain.LeaguesRequest;
import com.malkomich.football.data.batch.leagues.domain.LeaguesResponse;
import com.malkomich.football.data.batch.leagues.schema.LeaguesPageSchema;
import com.malkomich.football.data.batch.repository.domain.league.LeagueEntity;
import com.malkomich.football.data.batch.repository.service.FootballRepository;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingRequest;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.scrapping.service.ScrappingService;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
public class LeaguesServiceImpl implements LeaguesService {



    private ScrappingService scrappingService;
    private FootballRepository footballRepository;
    private LeagueAdapter adapter;
    private LeagueEntityAdapter repositoryAdapter;

    @Override
    public LeaguesService execute(final LeaguesRequest request, final Handler<AsyncResult<LeaguesResponse>> handler) {
        CompositeFuture.join(request.getLeaguesEndpoints().stream()
                .map(this::buildScrappingFuture)
                .collect(Collectors.toList()))
            .setHandler(scrappingResponseAsyncResult -> {
                if (scrappingResponseAsyncResult.failed()) {
                    handler.handle(Future.failedFuture(scrappingResponseAsyncResult.cause()));
                    return;
                }
                handleSuccessfullReply(scrappingResponseAsyncResult.result(), handler);
            });
        return this;
    }

    @Override
    public void close() {
        scrappingService.close();
        footballRepository.close();
    }

    private Future<ScrappingResponse> buildScrappingFuture(final String section) {
        final PageSchema pageSchema = LeaguesPageSchema.builder().build();
        Future<ScrappingResponse> future = Future.future();
        val request = ScrappingRequest.builder()
            .url(pageSchema.getURL(section))
            .containerSelector(pageSchema.containerSelector())
            .fieldSelectors(pageSchema.fields())
            .build();
        scrappingService.execute(request, future.completer());
        return future;
    }

    private void handleSuccessfullReply(final CompositeFuture compositeFuture,
                                        final Handler<AsyncResult<LeaguesResponse>> handler) {
        final LeaguesResponse response = adapter.execute(compositeFuture);
        saveToRepository(response);
        log.info("Leagues Service successfully executed");
        handler.handle(Future.succeededFuture(response));
    }

    private void saveToRepository(final LeaguesResponse response) {
        final List<LeagueEntity> entities = repositoryAdapter.execute(response);
        footballRepository.saveLeagues(entities, voidAsyncResult -> {
            if (voidAsyncResult.failed()) {
                log.error("Error saving leagues in football repository: {}", voidAsyncResult.cause().getMessage());
            }
        });
    }
}
