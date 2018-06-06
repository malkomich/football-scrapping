package com.malkomich.football.data.batch.configuration.verticles;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.malkomich.football.data.batch.leagues.LeaguesVerticle;
import com.malkomich.football.data.batch.leagues.adapter.LeagueAdapter;
import com.malkomich.football.data.batch.leagues.adapter.LeagueEntityAdapter;
import com.malkomich.football.data.batch.leagues.service.LeaguesService;
import com.malkomich.football.data.batch.leagues.service.LeaguesServiceImpl;
import com.malkomich.football.data.batch.matchDetails.MatchDetailsVerticle;
import com.malkomich.football.data.batch.matchDetails.adapter.MatchDetailsAdapter;
import com.malkomich.football.data.batch.matchDetails.service.MatchDetailsService;
import com.malkomich.football.data.batch.matchDetails.service.MatchDetailsServiceImpl;
import com.malkomich.football.data.batch.matches.MatchVerticle;
import com.malkomich.football.data.batch.matches.adapter.MatchEntityAdapter;
import com.malkomich.football.data.batch.matches.adapter.MatchAdapter;
import com.malkomich.football.data.batch.matches.service.MatchService;
import com.malkomich.football.data.batch.matches.service.MatchServiceImpl;
import com.malkomich.football.data.batch.ranks.adapter.RanksAdapter;
import com.malkomich.football.data.batch.ranks.adapter.RanksEntityAdapter;
import com.malkomich.football.data.batch.ranks.service.RanksService;
import com.malkomich.football.data.batch.ranks.service.RanksServiceImpl;
import com.malkomich.football.data.batch.repository.RepositoryVerticle;
import com.malkomich.football.data.batch.repository.service.FootballMongoRepository;
import com.malkomich.football.data.batch.repository.service.FootballRepository;
import com.malkomich.football.data.batch.scrapping.ScrappingVerticle;
import com.malkomich.football.data.batch.scrapping.service.ScrappingService;
import com.malkomich.football.data.batch.scrapping.service.ScrappingServiceImpl;
import com.malkomich.football.data.batch.teams.TeamsVerticle;
import com.malkomich.football.data.batch.teams.adapter.TeamsAdapter;
import com.malkomich.football.data.batch.teams.adapter.TeamsEntityAdapter;
import com.malkomich.football.data.batch.teams.service.TeamsService;
import com.malkomich.football.data.batch.teams.service.TeamsServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VerticlesConfigModule extends AbstractModule {

    private Vertx vertx;
    private JsonObject config;

    @Override
    protected void configure() {
        bind(FootballRepository.class).toInstance(repository());
        bind(ScrappingService.class).toInstance(scrappingRepository());
    }

    @Singleton
    @Provides
    public LeaguesService leaguesService(final FootballRepository footballRepository,
                                         final ScrappingService scrappingService) {
        final LeagueAdapter adapter = LeagueAdapter.builder().build();
        final LeagueEntityAdapter repositoryAdapter = LeagueEntityAdapter.builder().build();
        LeaguesService.createProxy(vertx, LeaguesVerticle.QUEUE);
        return LeaguesServiceImpl.builder()
            .footballRepository(footballRepository)
            .scrappingService(scrappingService)
            .adapter(adapter)
            .repositoryAdapter(repositoryAdapter)
            .build();
    }

    @Singleton
    @Provides
    public RanksService ranksService(final FootballRepository footballRepository,
                                       final ScrappingService scrappingService) {
        final RanksAdapter adapter = RanksAdapter.builder().build();
        final RanksEntityAdapter repositoryAdapter = RanksEntityAdapter.builder().build();
        RanksService.createProxy(vertx, MatchVerticle.QUEUE);
        return RanksServiceImpl.builder()
            .footballRepository(footballRepository)
            .scrappingService(scrappingService)
            .adapter(adapter)
            .repositoryAdapter(repositoryAdapter)
            .build();
    }

    @Singleton
    @Provides
    public MatchService matchesService(final FootballRepository footballRepository,
                                       final ScrappingService scrappingService) {
        final MatchAdapter adapter = MatchAdapter.builder().build();
        final MatchEntityAdapter repositoryAdapter = MatchEntityAdapter.builder().build();
        MatchService.createProxy(vertx, MatchVerticle.QUEUE);
        return MatchServiceImpl.builder()
            .footballRepository(footballRepository)
            .scrappingService(scrappingService)
            .adapter(adapter)
            .repositoryAdapter(repositoryAdapter)
            .build();
    }

    @Singleton
    @Provides
    public TeamsService teamsService(final FootballRepository footballRepository,
                                     final ScrappingService scrappingService) {
        final TeamsAdapter adapter = TeamsAdapter.builder().build();
        final TeamsEntityAdapter repositoryAdapter = TeamsEntityAdapter.builder().build();
        TeamsService.createProxy(vertx, TeamsVerticle.QUEUE);
        return TeamsServiceImpl.builder()
            .footballRepository(footballRepository)
            .scrappingService(scrappingService)
            .adapter(adapter)
            .repositoryAdapter(repositoryAdapter)
            .build();
    }

    @Singleton
    @Provides
    public MatchDetailsService matchDetailsService(final FootballRepository footballRepository,
                                                   final ScrappingService scrappingService) {
        final MatchDetailsAdapter adapter = MatchDetailsAdapter.builder().build();
        MatchDetailsService.createProxy(vertx, MatchDetailsVerticle.QUEUE);
        return MatchDetailsServiceImpl.builder()
            .footballRepository(footballRepository)
            .scrappingService(scrappingService)
            .adapter(adapter)
            .build();
    }

    private FootballRepository repository() {
        FootballRepository.createProxy(vertx, RepositoryVerticle.QUEUE);
        return FootballMongoRepository.builder()
            .client(WebClient.create(vertx))
            .config(config)
            .build();
    }

    private ScrappingService scrappingRepository() {
        ScrappingService.createProxy(vertx, ScrappingVerticle.QUEUE);
        return ScrappingServiceImpl.builder()
            .build();
    }
}
