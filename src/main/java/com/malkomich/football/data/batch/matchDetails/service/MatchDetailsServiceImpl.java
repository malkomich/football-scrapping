package com.malkomich.football.data.batch.matchDetails.service;

import com.malkomich.football.data.batch.matchDetails.adapter.MatchDetailsAdapter;
import com.malkomich.football.data.batch.matchDetails.domain.MatchDetailsRequest;
import com.malkomich.football.data.batch.matchDetails.domain.MatchDetailsResponse;
import com.malkomich.football.data.batch.matchDetails.schema.MatchDetailsPageSchema;
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

@Slf4j
@Builder
public class MatchDetailsServiceImpl implements MatchDetailsService {

    private static final String ENDPOINT_FORMAT = "partido/%s/%s/%d";

    private ScrappingService scrappingService;
    private FootballRepository footballRepository;
    private MatchDetailsAdapter adapter;

    @Override
    public MatchDetailsService execute(final MatchDetailsRequest request,
                                       final Handler<AsyncResult<MatchDetailsResponse>> handler) {
        buildScrappingFuture(request).setHandler(scrappingResponseAsyncResult -> {
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

    private Future<ScrappingResponse> buildScrappingFuture(final MatchDetailsRequest request) {
        final PageSchema pageSchema = MatchDetailsPageSchema.builder().build();
        final Future<ScrappingResponse> future = Future.future();
        final String endpoint = String.format(ENDPOINT_FORMAT,
            request.getLocalTeam(), request.getVisitorTeam(), request.getSeason());
        val scrappingRequest = ScrappingRequest.builder()
            .url(pageSchema.getURL(endpoint))
            .containerSelector(pageSchema.containerSelector())
            .fieldSelectors(pageSchema.fields())
            .build();
        scrappingService.execute(scrappingRequest, future.completer());
        return future;
    }

    private void handleSuccessfullReply(final ScrappingResponse scrappingResponse,
                                        final Handler<AsyncResult<MatchDetailsResponse>> handler) {
        final MatchDetailsResponse response = adapter.execute(scrappingResponse);
        log.info("Match Details Service successfully executed"); // TODO: Debug
        handler.handle(Future.succeededFuture(response));
    }
}
