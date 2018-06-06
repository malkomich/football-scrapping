package com.malkomich.football.data.batch.ranks.service;

import com.malkomich.football.data.batch.ranks.adapter.RanksAdapter;
import com.malkomich.football.data.batch.ranks.adapter.RanksEntityAdapter;
import com.malkomich.football.data.batch.ranks.domain.RanksRequest;
import com.malkomich.football.data.batch.ranks.domain.RanksResponse;
import com.malkomich.football.data.batch.ranks.schema.RankPageSchema;
import com.malkomich.football.data.batch.repository.domain.rank.RanksEntity;
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
public class RanksServiceImpl implements RanksService {

    private static final String ENDPOINT_FORMAT = "%s%d/grupo%d";

    private ScrappingService scrappingService;
    private FootballRepository footballRepository;
    private RanksAdapter adapter;
    private RanksEntityAdapter repositoryAdapter;

    @Override
    public RanksService execute(final RanksRequest request, final Handler<AsyncResult<RanksResponse>> handler) {
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

    private Future<ScrappingResponse> buildScrappingFuture(final RanksRequest request) {
        final PageSchema pageSchema = RankPageSchema.builder().build();
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
                                        final Handler<AsyncResult<RanksResponse>> handler) {
        final RanksResponse response = adapter.execute(scrappingResponse);
        saveToRepository(response);
        log.info("Ranks Service successfully executed");
        handler.handle(Future.succeededFuture(response));
    }

    private void saveToRepository(final RanksResponse response) {
        final List<RanksEntity> entities = repositoryAdapter.execute(response);
        footballRepository.saveRanks(entities, voidAsyncResult -> {
            if (voidAsyncResult.failed()) {
                log.error("Error saving ranks in football repository: {}", voidAsyncResult.cause().getMessage());
            }
        });
    }
}
