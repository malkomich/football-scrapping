package com.malkomich.football.data.batch;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.malkomich.football.data.batch.batch.BatchExecutor;
import com.malkomich.football.data.batch.configuration.mapper.MapperConfigModule;
import com.malkomich.football.data.batch.configuration.properties.PropertiesConfig;
import com.malkomich.football.data.batch.configuration.properties.PropertiesConfigModule;
import com.malkomich.football.data.batch.configuration.verticles.VerticlesConfigModule;
import com.malkomich.football.data.batch.configuration.vertx.GuiceVerticleFactory;
import com.malkomich.football.data.batch.configuration.vertx.GuiceVertxDeploymentManager;
import com.malkomich.football.data.batch.leagues.LeaguesVerticle;
import com.malkomich.football.data.batch.leagues.service.LeaguesService;
import com.malkomich.football.data.batch.matchDetails.MatchDetailsVerticle;
import com.malkomich.football.data.batch.matches.MatchVerticle;
import com.malkomich.football.data.batch.matches.service.MatchService;
import com.malkomich.football.data.batch.ranks.service.RanksService;
import com.malkomich.football.data.batch.repository.RepositoryVerticle;
import com.malkomich.football.data.batch.scrapping.ScrappingVerticle;
import com.malkomich.football.data.batch.teams.TeamsVerticle;
import com.malkomich.football.data.batch.teams.service.TeamsService;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;

public class ServiceLauncher {
    private static final long BLOCK_THREAD_CHECK_INTERVAL = 60000L;
    private static final Integer SCRAPPING_VERTICLE_THREADS = 10;

    @Inject
    private PropertiesConfig propertiesConfig;
    private Vertx vertx;

    public static void main(final String[] args) {
        new ServiceLauncher().launch();
    }

    private void launch() {
        final VertxOptions options = new VertxOptions()
            .setBlockedThreadCheckInterval(BLOCK_THREAD_CHECK_INTERVAL);
        vertx = Vertx.vertx(options);

        Guice.createInjector(new PropertiesConfigModule(vertx))
            .injectMembers(this);

        propertiesConfig.config(this::injectAndDeployVerticles);
    }

    private void injectAndDeployVerticles(final AsyncResult<JsonObject> asyncResult) {
        System.setProperty("ui4j.headless", "true");

        final Injector injector = dependencyModulesInjector(asyncResult.result());
        final GuiceVerticleFactory guiceVerticleFactory = new GuiceVerticleFactory(injector);
        vertx.registerVerticleFactory(guiceVerticleFactory);

        compositeFuture(asyncResult.result(), injector);
    }

    private Injector dependencyModulesInjector(final JsonObject config) {
        return Guice.createInjector(
            new MapperConfigModule(),
            new VerticlesConfigModule(vertx, config)
        );
    }

    private void compositeFuture(final JsonObject config, final Injector injector) {
        generateCompositeFuture(config).setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                new BatchExecutor(vertx,
                                  injector.getInstance(LeaguesService.class),
                                  injector.getInstance(RanksService.class),
                                  injector.getInstance(MatchService.class),
                                  injector.getInstance(TeamsService.class))
                    .execute();
            }
        });
    }

    private CompositeFuture generateCompositeFuture(final JsonObject config) {
        GuiceVertxDeploymentManager deploymentManager = new GuiceVertxDeploymentManager(vertx);

        return CompositeFuture.all(
            deploymentManager.deployVerticle(ScrappingVerticle.class, config, SCRAPPING_VERTICLE_THREADS),
            deploymentManager.deployVerticle(RepositoryVerticle.class, config),
            deploymentManager.deployVerticle(LeaguesVerticle.class, config),
            deploymentManager.deployVerticle(MatchVerticle.class, config),
            deploymentManager.deployVerticle(TeamsVerticle.class, config),
            deploymentManager.deployVerticle(MatchDetailsVerticle.class, config)
        );
    }
}
