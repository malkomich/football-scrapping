package com.malkomich.football.data.batch.teams.service;

import com.malkomich.football.data.batch.repository.domain.team.TeamEntity;
import com.malkomich.football.data.batch.repository.service.FootballRepository;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingRequest;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.scrapping.service.ScrappingService;
import com.malkomich.football.data.batch.teams.adapter.TeamsAdapter;
import com.malkomich.football.data.batch.teams.adapter.TeamsEntityAdapter;
import com.malkomich.football.data.batch.teams.domain.TeamsRequest;
import com.malkomich.football.data.batch.teams.domain.TeamsResponse;
import com.malkomich.football.data.batch.teams.schema.TeamPageSchema;
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
public class TeamsServiceImpl implements TeamsService {

    private static final String ENDPOINT_FORMAT = "%s%d/grupo%d/equipos";

    private ScrappingService scrappingService;
    private FootballRepository footballRepository;
    private TeamsAdapter adapter;
    private TeamsEntityAdapter repositoryAdapter;

    @Override
    public TeamsService execute(final TeamsRequest request, final Handler<AsyncResult<TeamsResponse>> handler) {
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

    private Future<ScrappingResponse> buildScrappingFuture(final TeamsRequest request) {
        final PageSchema pageSchema = TeamPageSchema.builder().build();
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
                                        final Handler<AsyncResult<TeamsResponse>> handler) {
        final TeamsResponse response = adapter.execute(scrappingResponse);
        saveToRepository(response);
        log.info("Team Service successfully executed");
        handler.handle(Future.succeededFuture(response));
    }

    private void saveToRepository(final TeamsResponse response) {
        final List<TeamEntity> entities = repositoryAdapter.execute(response);
        footballRepository.saveTeams(entities, voidAsyncResult -> {
            if (voidAsyncResult.failed()) {
                log.error("Error saving teams in football repository: {}", voidAsyncResult.cause().getMessage());
            }
        });
    }
}
