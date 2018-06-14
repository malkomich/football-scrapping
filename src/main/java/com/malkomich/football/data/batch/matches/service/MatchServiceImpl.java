package com.malkomich.football.data.batch.matches.service;

import com.malkomich.football.data.batch.matchDetails.service.MatchDetailsService;
import com.malkomich.football.data.batch.matches.adapter.MatchAdapter;
import com.malkomich.football.data.batch.matches.adapter.MatchEntityAdapter;
import com.malkomich.football.data.batch.matches.domain.MatchesRequest;
import com.malkomich.football.data.batch.matches.domain.MatchesResponse;
import com.malkomich.football.data.batch.matches.schema.MatchesPageSchema;
import com.malkomich.football.data.batch.repository.domain.match.MatchEntity;
import com.malkomich.football.data.batch.repository.service.FootballRepository;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingRequest;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.scrapping.service.ScrappingService;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;

@Slf4j
@Builder
public class MatchServiceImpl implements MatchService {

    private static final String ENDPOINT_FORMAT = "%s%d/grupo%d/calendario";

    private ScrappingService scrappingService;
    private FootballRepository footballRepository;
    private MatchAdapter adapter;
    private MatchEntityAdapter repositoryAdapter;
    private MatchDetailsService detailsService;

    @Override
    public MatchService execute(final MatchesRequest request, final Handler<AsyncResult<MatchesResponse>> handler) {
        buildScrappingFuture(request).setHandler(scrappingResponseAsyncResult -> {
            if (scrappingResponseAsyncResult.failed()) {
                handler.handle(Future.failedFuture(scrappingResponseAsyncResult.cause()));
                return;
            }
            handleSuccessfullReply(scrappingResponseAsyncResult.result(), handler, request);
        });
        return this;
    }

    @Override
    public void close() {
        scrappingService.close();
        footballRepository.close();
    }

    private Future<ScrappingResponse> buildScrappingFuture(final MatchesRequest request) {
        final PageSchema pageSchema = MatchesPageSchema.builder().build();
        final Future<ScrappingResponse> future = Future.future();
        final String endpoint = String.format(ENDPOINT_FORMAT,
            request.getLeague(), request.getSeason(), request.getGroup());
        val scrappingRequest = ScrappingRequest.builder()
            .url(pageSchema.getURL(endpoint))
            .containerSelector(pageSchema.containerSelector())
            .fieldSelectors(pageSchema.fields())
            .build();
        scrappingService.execute(scrappingRequest, future.completer());
        return future;
    }

    private void handleSuccessfullReply(final ScrappingResponse scrappingResponse,
                                        final Handler<AsyncResult<MatchesResponse>> handler,
                                        final MatchesRequest request) {
        final MatchesResponse response = adapter.execute(scrappingResponse, request.getSeason());
        // TODO : Call Match Details Service to get the match statistics
        saveToRepository(response);
        log.info("Matches Service successfully executed");
        handler.handle(Future.succeededFuture(response));
    }

    private void saveToRepository(final MatchesResponse response) {
        final List<MatchEntity> entities = repositoryAdapter.execute(response);
        footballRepository.saveMatches(entities, voidAsyncResult -> {
            if (voidAsyncResult.failed()) {
                log.error("Error saving matches in football repository: {}", voidAsyncResult.cause().getMessage());
            }
        });
    }
}
